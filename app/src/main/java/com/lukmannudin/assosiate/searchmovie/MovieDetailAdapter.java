package com.lukmannudin.assosiate.searchmovie;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lukmannudin.assosiate.searchmovie.Model.Genre;
import com.lukmannudin.assosiate.searchmovie.Model.MovieTrending;

import java.util.ArrayList;
import java.util.List;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.MovieViewHolder> {
    private List<Genre> genreList;


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.genre_list, parent, false);
        return new MovieViewHolder(view);
    }

    public MovieDetailAdapter(List<Genre> genreList) {
        this.genreList = genreList;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.genre.setText(genreList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return genreList.size();
    }


    class MovieViewHolder extends RecyclerView.ViewHolder {
        private TextView genre;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            genre = itemView.findViewById(R.id.genreText);
        }

    }
}
