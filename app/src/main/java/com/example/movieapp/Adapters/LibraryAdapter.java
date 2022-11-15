package com.example.movieapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp.Models.Library;
import com.example.movieapp.R;


import java.util.List;

public class LibraryAdapter extends RecyclerView.Adapter<LibraryAdapter.ViewHolder> {
    private Context context;
    private List<Library> allitems;

    public LibraryAdapter(Context context, List<Library>items) {
        this.context = context;
        this.allitems = items;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_added_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Library library = allitems.get(position);
        holder.bind(library);
    }

    @Override
    public int getItemCount() {
        return allitems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivMovieImage;
        private TextView tvMovieName;

        public ViewHolder(View itemView) {

            super(itemView);
            ivMovieImage = itemView.findViewById(R.id.ivMovieImage);
            tvMovieName = itemView.findViewById(R.id.tvMovieName);
        }

        public void bind(Library lib) {
            tvMovieName.setText(lib.getMovieName());
            String img = lib.getMovieImage();
            img = String.format("https://image.tmdb.org/t/p/w342/%s",img);
            Glide.with(context).load(img).into(ivMovieImage);
        }
    }

    public void removeItem(int position)
    {
        allitems.remove(position);
        notifyItemRemoved(position);
    }
    public void addItem(int position, Library lib)
    {
        allitems.add(lib);
        notifyItemInserted(position);
    }

}
