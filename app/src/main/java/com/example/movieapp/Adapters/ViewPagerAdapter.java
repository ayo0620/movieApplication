package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.movieapp.Fragments.CategoryFragment;
import com.example.movieapp.Fragments.RecommendationFragment;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private Context mycontext;
    int totalTabs;

    public ViewPagerAdapter(@NonNull Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        mycontext = context;
        this.totalTabs = totalTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new CategoryFragment();
                break;
            case 1:
                fragment = new RecommendationFragment();
                break;
            default:
                return null;
        }
        return fragment;
    }


    @Override
    public int getCount() {
        return  totalTabs;
    }

}