package com.lukmannudin.assosiate.searchmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movieList = new ArrayList<>();
    private Context context;


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    public MovieAdapter(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185/"+movieList.get(position).getPosterPath())
                .into(holder.posterImage);
        holder.title.setText(movieList.get(position).getTitle());
        holder.description.setText(movieList.get(position).getOverview());
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImage;
        private TextView title,description;
        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.ivPosterImage);
            title = itemView.findViewById(R.id.tvTitleMovie);
            description = itemView.findViewById(R.id.tvMovieDescription);
        }
    }
}
