package com.example.movieapp.Models;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Recommendations")
public class Recommendations extends ParseObject {
    public static final String KEY_USER = "user";
    public static final String KEY_MOVIE_ID = "movie_id";

    public String getUser(){
        return getString(KEY_USER);
    }

    public void setUser(ParseUser user)
    {
        put(KEY_USER,user);
    }

    public void setMovieId(String lastName)
    {
        put(KEY_MOVIE_ID,lastName);
    }
    public String getMovieId(){return getString(KEY_MOVIE_ID);}

}

