package com.example.movieapp.Fragments;

import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.movieapp.Adapters.LibraryAdapter;
import com.example.movieapp.Models.Library;
import com.example.movieapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class LibraryFragment extends Fragment {
//    private ActivityMainBinding binding;
    private RecyclerView rvLibrary;
    private ImageView libraryClose;
    LibraryAdapter adapter;
    List<Library> allItems;
    public LibraryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvLibrary = view.findViewById(R.id.rvLibrary);

        allItems = new ArrayList<>();
        adapter = new LibraryAdapter(getContext(),allItems);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvLibrary.setLayoutManager(layoutManager);
        rvLibrary.setAdapter(adapter);
        queryLibraryItems();


//        swipe to delete
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Library deletedCourse = allItems.get(viewHolder.getAdapterPosition());
                int position = viewHolder.getAdapterPosition();

                allItems.remove(position);
                adapter.notifyItemRemoved(position);
                ParseQuery<Library> query = ParseQuery.getQuery(Library.class);
                query.include("createdAt");
                query.whereEqualTo("movieName",deletedCourse.getMovieName());
                query.addDescendingOrder("createdAt");
                query.findInBackground(new FindCallback<Library>() {
                    @Override
                    public void done(List<Library> objects, ParseException e) {
                        if (e != null) {
                            Log.i("NotifyAdapter", e.toString());
                        }
                        objects.get(0).deleteInBackground();
                    }
                });
                Snackbar.make(rvLibrary, "deleted "+deletedCourse.getMovieName(), Snackbar.LENGTH_SHORT).setAction("undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Library library = new Library();
                        library.setMovieImage(deletedCourse.getMovieImage());
                        library.setForUser(ParseUser.getCurrentUser());
                        library.setMovieName(deletedCourse.getMovieName());
                        library.saveInBackground();
                        Log.i("snackbar", String.valueOf(position));
                        allItems.add(position, deletedCourse);
                        adapter.notifyItemInserted(position);
                    }
                }).show();
            }
        }).attachToRecyclerView(rvLibrary);
    }


    private void queryLibraryItems() {
        ParseQuery<Library> libraryParseQuery = ParseQuery.getQuery(Library.class);
        libraryParseQuery.whereContains("forUser", ParseUser.getCurrentUser().getObjectId());
        libraryParseQuery.addDescendingOrder("createdAt");
        libraryParseQuery.findInBackground(new FindCallback<Library>() {
            @Override
            public void done(List<Library> objects, ParseException e) {
                if(e!= null)
                {
                    Log.i("LibraryFragment", "issue with getting library objects",e);
                }
                allItems.addAll(objects);
                adapter.notifyDataSetChanged();
                Log.i("queryLib", allItems.toString());
            }
        });
    }
}