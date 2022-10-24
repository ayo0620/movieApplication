package com.example.movieapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.example.movieapp.Models.Movies;
import com.example.movieapp.MovieDetailActivity;
import com.example.movieapp.R;
import com.google.android.material.snackbar.Snackbar;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> {
    private Context context;
    private  List<Movies> allCards;
    private Boolean isAddToLib;

    public CardsAdapter(Context context, List<Movies>cards) {
        this.context = context;
        this.allCards = cards;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movies cards = allCards.get(position);
        Log.i("Adapter",cards.toString());
        holder.bind(cards);
    }

    @Override
    public int getItemCount() {
        return allCards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivCardMovieImg;
        private TextView tvCardMovieTitle;
        private CardView mCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            ivCardMovieImg = itemView.findViewById(R.id.ivCardMovieImg);
            tvCardMovieTitle = itemView.findViewById(R.id.tvCardMovieTitle);
            mCardView = itemView.findViewById(R.id.card_view);
//            mCardView.setOnCreateContextMenuListener(this);
        }

        public void bind(Movies cards) {
            tvCardMovieTitle.setText(cards.getMovieName());
            String img = cards.getMovieImage();
            img = img.replace("{width}","496");
            img = img.replace("{height}","600");
            Glide.with(context).load(img).into(ivCardMovieImg);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movies clickCard = allCards.get(getAdapterPosition());
                    Intent i  = new Intent(context, MovieDetailActivity.class);
                    i.putExtra(Movies.class.getSimpleName(), Parcels.wrap(clickCard));
                    context.startActivity(i);
                }
            });
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//            menu.add(this.getAdapterPosition(), R.id.action_addToLibrary,0,"Add to library");
//        }

    }
//    public void addToLibrary(int position, FragmentActivity view)
//    {
//        isAddToLib = true;
//        Cards card = allCards.get(position);
//        ParseQuery<Library>libraryParseQuery = ParseQuery.getQuery(Library.class);
//        libraryParseQuery.whereContains("forUser",ParseUser.getCurrentUser().getObjectId());
//        libraryParseQuery.findInBackground(new FindCallback<Library>() {
//            @Override
//            public void done(List<Library> objects, ParseException e) {
//                for (Library lib: objects)
//                {
//                    if (lib.getGameName().equals(card.getName()))
//                    {
//                        isAddToLib = false;
//                        Snackbar.make(view.findViewById(R.id.rlExplore),"Already added",Snackbar.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
//                if(isAddToLib.equals(true))
//                {
//                    Library library = new Library();
//                    library.setGameName(card.getName());
//                    library.setGameImage(card.getImage());
//                    library.setForUser(ParseUser.getCurrentUser());
//                    library.saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(ParseException e) {
//                            if(e!= null)
//                            {
//                                Log.i("Library", "issue with saving library object",e);
//                            }
//                        }
//                    });
//                }
//            }
//        });
//
//
//    }

}
