package com.example.movieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.movieapp.Fragments.ActivityFragment;
import com.example.movieapp.Fragments.HomeFeedFragment;
import com.example.movieapp.Fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public BottomNavigationView bottomNavigationView;
    FragmentManager fragmentManager = getSupportFragmentManager();

    HomeFeedFragment homeFeedFragment = new HomeFeedFragment();
    SearchFragment searchFragment = new SearchFragment();
    ActivityFragment activityFragment = new ActivityFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
//        Navigation_drawer
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                switch (item.getItemId()) {
                    case R.id.action_home_screen:
                        fragment = homeFeedFragment;
                        break;
                    case R.id.action_Search:
                        fragment = searchFragment;
                        break;
                    case R.id.action_explore:
                        fragment = activityFragment;
                        break;
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.action_home_screen);
    }
}