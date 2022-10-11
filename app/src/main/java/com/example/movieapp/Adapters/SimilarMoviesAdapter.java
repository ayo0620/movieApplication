package com.example.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.MovieDetailActivity;
import com.example.movieapp.R;

import org.parceler.Parcels;

import java.util.List;

public class SimilarMoviesAdapter extends RecyclerView.Adapter<SimilarMoviesAdapter.ViewHolder>{
    Context context;
    List<Movies> movies;

    public SimilarMoviesAdapter(Context context, List<Movies>movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_similar_movie, parent,false);
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
        private ImageView ivSimilarMovieImage;
        private TextView tvSimilarMovieName;

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            if(position != RecyclerView.NO_POSITION)
            {
                Movies movie = movies.get(position);
                Intent intent = new Intent(context, MovieDetailActivity.class);
                intent.putExtra(Movies.class.getSimpleName(), Parcels.wrap(movie));
                context.startActivity(intent);
            }
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivSimilarMovieImage= itemView.findViewById(R.id.ivSimilarMovieImage);
            tvSimilarMovieName = itemView.findViewById(R.id.tvSimilarMovieName);
            itemView.setOnClickListener(this);
        }


        public void bind(Movies movie)
        {
            tvSimilarMovieName.setText(movie.getMovieName());

            String imageUrl;
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            {
                imageUrl = movie.getMovieBackDrop();
            }
            else{
                imageUrl = movie.getMovieImage();
            }
            int radius = 20;

            Glide.with(context).load(imageUrl).fitCenter().transform(new RoundedCorners(radius)).placeholder(R.drawable.placeholder).into(ivSimilarMovieImage);
        }
    }


}
