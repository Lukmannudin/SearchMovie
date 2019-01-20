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
import com.lukmannudin.assosiate.searchmovie.Model.MovieTrending;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<MovieTrending> movieTrendingList;
    private Context context;
    private MainActivity.itemClickListener listener;


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list, parent, false);
        return new MovieViewHolder(view);
    }

    MovieAdapter(List<MovieTrending> movieTrendingList, MainActivity.itemClickListener listener) {
        this.movieTrendingList = movieTrendingList;
        this.listener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w185/" + movieTrendingList.get(position).getPosterPath())
                .into(holder.posterImage);
        holder.title.setText(movieTrendingList.get(position).getTitle());
        holder.description.setText(movieTrendingList.get(position).getOverview());
        holder.onClickHandler(
                movieTrendingList.get(position).getId().toString(),
                movieTrendingList.get(position).getOriginalTitle(),
                listener);

    }

    @Override
    public int getItemCount() {
        return movieTrendingList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView posterImage;
        private TextView title, description;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            posterImage = itemView.findViewById(R.id.ivPosterImage);
            title = itemView.findViewById(R.id.tvTitleMovie);
            description = itemView.findViewById(R.id.tvMovieDescription);
        }

        void onClickHandler(final String movieId, final String movieName, final MainActivity.itemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.openDetailMovie(movieId, movieName);
                }
            });
        }
    }
}
