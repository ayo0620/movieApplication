package com.example.movieapp.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.Movie;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.movieapp.Adapters.MovieSearchAdapter;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class SearchFragment extends Fragment {
    RecyclerView rvSearchMovies;
    androidx.appcompat.widget.SearchView searchView;
    private TextView tvSearchPlaceholder;
    protected MovieSearchAdapter adapter;
    protected List<Movies> movieSearchResult;
    public static final String TAG = SearchFragment.class.getSimpleName();


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvSearchMovies = view.findViewById(R.id.rvSearchMovies);
        searchView = view.findViewById(R.id.searchMovie);
        tvSearchPlaceholder = view.findViewById(R.id.tvSearchPlaceholder);

        movieSearchResult = new ArrayList<>();
        adapter = new MovieSearchAdapter(getContext(), movieSearchResult);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        tvSearchPlaceholder.setVisibility(View.VISIBLE);

        rvSearchMovies.setLayoutManager(layoutManager);
        rvSearchMovies.setAdapter(adapter);
        rvSearchMovies.setHasFixedSize(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                tvSearchPlaceholder.setVisibility(View.GONE);
                fetchMovie(query);
                Log.i("diff",movieSearchResult.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void fetchMovie(String query) {
        movieSearchResult.clear();
        AsyncHttpClient client = new AsyncHttpClient();

        String SEARCH_MOVIE_URL = "https://api.themoviedb.org/3/search/movie?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US&query="+query+"&page=1";
        client.get(SEARCH_MOVIE_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    movieSearchResult.addAll(Movies.fromJsonArray(results));
                    adapter.notifyDataSetChanged();
                    Log.i(TAG, "movies"+ movieSearchResult.size());
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

        adapter.notifyDataSetChanged();
    }


}