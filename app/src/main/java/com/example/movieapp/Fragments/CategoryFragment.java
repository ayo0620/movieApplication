package com.example.movieapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.Adapters.CardsAdapter;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class CategoryFragment extends Fragment {
    Spinner spinner;
    RecyclerView rvMovieCategories;
    List<Movies> allcards;
    CardsAdapter adapter;
    TextView sortedByText;
    public static final String TAG = CategoryFragment.class.getSimpleName();

    public CategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinner = view.findViewById(R.id.categorySpinner);
        rvMovieCategories = view.findViewById(R.id.rvMovieCategories);
        sortedByText = view.findViewById(R.id.sortedByText);

        allcards = new ArrayList<>();
        adapter = new CardsAdapter(getContext(), allcards);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rvMovieCategories.setLayoutManager(layoutManager);
        rvMovieCategories.setAdapter(adapter);
        rvMovieCategories.setHasFixedSize(true);

        //        spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(myAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(spinner.getSelectedItem().equals("popular"))
                {
                    sortedByText.setText("popular Movies");
                    sortByPopularity();
                }

                if (view != null) {
                    //do things here

                    if(spinner.getSelectedItem().equals("Top Rated"))
                    {
                        sortedByText.setText("Top Rated Movies");
                        allcards.clear();
                        sortByTopRated();
                    }

                    if(spinner.getSelectedItem().equals("Now playing"))
                    {
                        sortedByText.setText("Now Playing");
                        allcards.clear();
                        sortByNowPlaying();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    private void sortByNowPlaying() {
        allcards.clear();
        AsyncHttpClient client = new AsyncHttpClient();

        String NP_MOVIE_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
        client.get(NP_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    allcards.addAll(Movies.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "movies"+ allcards.size());
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

    private void sortByTopRated() {
        allcards.clear();
        AsyncHttpClient client = new AsyncHttpClient();

        String TOP_RATED_MOVIE_URL = "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
        client.get(TOP_RATED_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    allcards.addAll(Movies.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "movies"+ allcards.size());
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

    private void sortByPopularity() {
        allcards.clear();
        AsyncHttpClient client = new AsyncHttpClient();

        String POPULAR_MOVIE_URL = "https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
        client.get(POPULAR_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    allcards.addAll(Movies.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "movies"+ allcards.size());
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

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_play_Trailer:
                adapter.playTrailer(item.getGroupId(), getActivity());
                return true;
            case R.id.action_recommend:
                adapter.addToRecommendations(item.getGroupId(), getActivity());
                Snackbar.make(getView().findViewById(R.id.rlCategory),"Added to your Recommendation list",Snackbar.LENGTH_SHORT).show();
                return true;
        }
        return super.onContextItemSelected(item);
    }



}