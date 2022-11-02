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
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.Adapters.CardsAdapter;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.Models.Recommendations;
import com.example.movieapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class RecommendationFragment extends Fragment {
    CardsAdapter cardsAdapter;
    List<Movies> allRecommendations;
    List<String> allMovieIDs;
    RecyclerView rvRecommendation;
    private TextView tvCurrentUser;
    public static final String TAG = RecommendationFragment.class.getSimpleName();

    public RecommendationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recommendation, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvRecommendation = view.findViewById(R.id.rvRecommendation);
        tvCurrentUser = view.findViewById(R.id.tvCurrentUser);

        allRecommendations = new ArrayList<>();
        allMovieIDs = new ArrayList<>();
        cardsAdapter = new CardsAdapter(getContext(), allRecommendations);

        tvCurrentUser.setText("For "+ParseUser.getCurrentUser().getUsername());

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2);

        rvRecommendation.setLayoutManager(layoutManager);
        rvRecommendation.setAdapter(cardsAdapter);
        rvRecommendation.setHasFixedSize(true);
        cardsAdapter.notifyDataSetChanged();

        getUserRecommendations();
    }

    private void getUserRecommendations() {
        ParseQuery<Recommendations> query = ParseQuery.getQuery(Recommendations.class);
        query.whereContains("user", ParseUser.getCurrentUser().getObjectId());
        query.findInBackground(new FindCallback<Recommendations>() {
            @Override
            public void done(List<Recommendations> objects, ParseException e) {
                for(Recommendations recommend: objects)
                {
                    allMovieIDs.add(recommend.getMovieId());
                }
                if(e != null)
                {
                    Log.i(TAG, "can't get recommendations", e);
                }

                allRecommendations.clear();
                AsyncHttpClient client = new AsyncHttpClient();
                Log.i(TAG, allMovieIDs.toString());
                for(String movie_id: allMovieIDs)
                {
                    String RECOMMENDATION_MOVIE_URL = "https://api.themoviedb.org/3/movie/"+movie_id+"/recommendations?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&page=1";
                    client.get(RECOMMENDATION_MOVIE_URL, new JsonHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Headers headers, JSON json) {
                            Log.d(TAG, "onSuccess");
                            JSONObject jsonObject = json.jsonObject;
                            try {
                                JSONArray results = jsonObject.getJSONArray("results");
                                allRecommendations.addAll(Movies.fromJsonArray(results));
                                cardsAdapter.notifyDataSetChanged();
                                Log.i(TAG, "movies"+ allRecommendations.size());
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
        });


    }

}