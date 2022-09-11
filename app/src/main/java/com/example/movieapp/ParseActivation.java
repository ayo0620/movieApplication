package com.example.movieapp;

import android.app.Application;

import androidx.appcompat.app.AppCompatActivity;
import com.parse.Parse;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ParseActivation extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        Register your parse model

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("772hQFtFcvtVDIThgqFPP5BGRPNsFFoWJqXbSRRO")
                .clientKey("YgfI5jh1rJkjfLUhvXBoz0VCLSofBNrIwo76qqjb")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}