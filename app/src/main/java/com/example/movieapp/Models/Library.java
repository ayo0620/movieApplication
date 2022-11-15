package com.example.movieapp.Models;


import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.File;


@ParseClassName("Library")
public class Library extends ParseObject {
    public static final String KEY_MOVIE_NAME = "movieName";
    public static final String KEY_MOVIE_IMAGE = "movieImage";
    public static final String KEY_FOR_USER = "forUser";

    public void setMovieImage(String gameImage) {
        put(KEY_MOVIE_IMAGE, gameImage);
    }

    public String getMovieImage() {
        return getString(KEY_MOVIE_IMAGE);
    }

    public void setMovieName(String gameName) {
        put(KEY_MOVIE_NAME, gameName);
    }

    public String getMovieName() {
        return getString(KEY_MOVIE_NAME);
    }

    public void setForUser(ParseUser parseUser) {
        put(KEY_FOR_USER, parseUser);
    }

    public ParseUser getForUser()
    {
        return getParseUser(KEY_FOR_USER);
    }
}