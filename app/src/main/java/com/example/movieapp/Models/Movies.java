package com.example.movieapp.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movies {
    public String  movieName;
    public String movieImage;
    public String movieDescription;
    public String movieYear;
    public String movieBackDrop;

    public Movies(){}

    public Movies(JSONObject jsonObject) throws JSONException {
        movieName = jsonObject.getString("title");
        movieImage = jsonObject.getString("poster_path");
        movieDescription = jsonObject.getString("overview");
        movieYear = jsonObject.getString("release_date");
        movieBackDrop = jsonObject.getString("backdrop_path");
    }


    public static List<Movies> fromJsonArray(JSONArray itemsJsonArray) throws JSONException {
        List<Movies> movies = new ArrayList<>();
        for (int i = 0; i < itemsJsonArray.length(); i++) {
            if(new Movies(itemsJsonArray.getJSONObject(i)).getMovieImage() != null){
                movies.add(new Movies(itemsJsonArray.getJSONObject(i)));
            }
        }
        return movies;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieImage() {
        return String.format("https://image.tmdb.org/t/p/w342/%s",movieImage);
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public String getMovieBackDrop(){
        return String.format("https://image.tmdb.org/t/p/w342/%s",movieBackDrop);
    }

}