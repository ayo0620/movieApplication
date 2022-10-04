package com.example.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.parceler.Parcel;
import org.parceler.Parcels;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.MovieDetailActivity;
import com.example.movieapp.R;


import java.util.List;

public class MovieSearchAdapter extends RecyclerView.Adapter<MovieSearchAdapter.ViewHolder>{
    Context context;
    List<Movies> movies;

    public MovieSearchAdapter(Context context, List<Movies>movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie_card, parent,false);
        return new ViewHolder(movieView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        Movies movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tvTitle;
        private TextView tvOverview;
        private ImageView ivMovieImg;
        private TextView tvMovieRestriction;

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION)
            {
                Movies movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.movieTitle);
            tvOverview = itemView.findViewById(R.id.tvShortMovieDesc);
            ivMovieImg = itemView.findViewById(R.id.ivMovieImage);
            tvMovieRestriction = itemView.findViewById(R.id.tvMovieYear);
            itemView.setOnClickListener(this);
        }


        public void bind(Movies movie)
        {
            tvTitle.setText(movie.getMovieName());
            tvOverview.setText(movie.getMovieDescription());
            String date = movie.getMovieYear();
            tvMovieRestriction.setText(date.substring(0,4));
            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getMovieBackDrop();
            }
            else{
                imageUrl = movie.getMovieImage();
            }
            int radius = 20;

            Glide.with(context).load(imageUrl).fitCenter().transform(new RoundedCorners(radius)).placeholder(R.drawable.placeholder).into(ivMovieImg);
        }
    }


}