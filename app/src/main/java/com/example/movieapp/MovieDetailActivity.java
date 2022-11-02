package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.Adapters.SimilarMoviesAdapter;
import com.example.movieapp.Models.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MovieDetailActivity extends AppCompatActivity {
    private ImageView MovieDetailClose;
    private TextView tvDetailMovieTitle;
    private ImageView ivDetailMovieImage;
    private TextView tvDetailMovieYear;
    private TextView tvDetailMovieGenre;
    private TextView tvDetailTimeFrame;
    private TextView tvDetailMovieDesc;
    private RecyclerView rvSimilarMovies;
    private Button btnWatchContent;
    private SimilarMoviesAdapter similarMoviesAdapter;
    public List<Movies>similarMovieList;
    public static final String TAG = MovieDetailActivity.class.getSimpleName();
    Movies movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        getSupportActionBar().hide();

        MovieDetailClose = findViewById(R.id.MovieDetailClose);
        tvDetailMovieTitle = findViewById(R.id.tvDetailMovieTitle);
        ivDetailMovieImage = findViewById(R.id.ivDetailMovieImage);
        tvDetailMovieYear = findViewById(R.id.tvDetailMovieYear);
        tvDetailMovieGenre = findViewById(R.id.tvDetailMovieGenre);
        tvDetailTimeFrame = findViewById(R.id.tvDetailTimeFrame);
        tvDetailMovieDesc = findViewById(R.id.tvDetailMovieDesc);
        rvSimilarMovies = findViewById(R.id.rvSimilarMovies);
        btnWatchContent = findViewById(R.id.btnWatchTrailer);


        movies = Parcels.unwrap(getIntent().getParcelableExtra(Movies.class.getSimpleName()));

        MovieDetailClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        similarMovieList = new ArrayList<>();

        tvDetailMovieTitle.setText(movies.getMovieName());
        String imageUrl;
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            imageUrl = movies.getMovieBackDrop();
        }
        else{
            imageUrl = movies.getMovieImage();
        }
        int radius = 30;

        Glide.with(this).load(imageUrl).fitCenter().transform(new RoundedCorners(radius)).placeholder(R.drawable.placeholder).into(ivDetailMovieImage);

        String date = movies.getMovieYear();
        tvDetailMovieYear.setText(date.substring(0,4));
        tvDetailMovieDesc.setText(movies.getMovieDescription());

        getMovieDetailQuery(movies.getMovieID());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rvSimilarMovies.setLayoutManager(linearLayoutManager);
        similarMoviesAdapter = new SimilarMoviesAdapter(this,similarMovieList);
        rvSimilarMovies.setAdapter(similarMoviesAdapter);

        getSimilarMovies(movies.getMovieID());

        btnWatchContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMovieTrailer(movies.getMovieID());
            }
        });
    }

    private void getMovieTrailer(String movieID) {
        AsyncHttpClient client = new AsyncHttpClient();
        String YT_MOVIE_URL = "https://api.themoviedb.org/3/movie/"+movieID+"/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
        client.get(YT_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    String key = results.getJSONObject(0).getString("key");
                    Intent browseIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/results?search_query="+key));
                    startActivity(browseIntent);
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void getSimilarMovies(String movieID) {
        similarMovieList.clear();
        AsyncHttpClient client = new AsyncHttpClient();

        String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/movie/"+movieID+"/similar?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
        client.get(SEARCH_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    similarMovieList.addAll(Movies.fromJsonArray(results));
                    similarMoviesAdapter.notifyDataSetChanged();
                    Log.i(TAG, "movies"+ similarMovieList.size());
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }

    private void getMovieDetailQuery(String movieID) {
        AsyncHttpClient client = new AsyncHttpClient();
        String MOVIE_Detail_URL = "https://api.themoviedb.org/3/movie/"+movieID+"?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
        client.get(MOVIE_Detail_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("genres");
                    String genre = results.getJSONObject(0).getString("name");
                    String runtime = jsonObject.getString("runtime");
                    tvDetailMovieGenre.setText(genre);
                    tvDetailTimeFrame.setText(movies.getTimeStamp(runtime));
                }
                catch (JSONException e)
                {
                    Log.e(TAG, "Hit JSON exception", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure");
            }
        });
    }
}