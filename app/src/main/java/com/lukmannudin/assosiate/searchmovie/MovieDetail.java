package com.lukmannudin.assosiate.searchmovie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lukmannudin.assosiate.searchmovie.APIRequest.MovieService;
import com.lukmannudin.assosiate.searchmovie.Model.Genre;
import com.lukmannudin.assosiate.searchmovie.Model.Movie;
import com.lukmannudin.assosiate.searchmovie.Model.MovieTrending;
import com.lukmannudin.assosiate.searchmovie.Response.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieDetail extends AppCompatActivity {
    private MovieDetailAdapter adapter;
    private String movieId,movieTitle;
    TextView movieName,movieScore,movieDuration,movieLang,movieOverview,movieRelease;
    CircleImageView posterImage;
    RecyclerView rvGenres;
    private List<Genre> genreList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);
        posterImage = findViewById(R.id.ciPosterImage);
        movieName = findViewById(R.id.tvMovieName);
        movieScore = findViewById(R.id.movieScore);
        movieDuration = findViewById(R.id.movieDuration);
        movieLang = findViewById(R.id.movieLanguage);
        movieOverview = findViewById(R.id.tvOverview);
        movieRelease = findViewById(R.id.tvRelease);
        rvGenres = findViewById(R.id.rvGenres);
        adapter = new MovieDetailAdapter(genreList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rvGenres.setLayoutManager(layoutManager);
        rvGenres.setAdapter(adapter);

        movieId = Objects.requireNonNull(getIntent().getExtras()).getString("movieId");
        movieTitle = Objects.requireNonNull(getIntent().getExtras()).getString("movieTitle");
        Objects.requireNonNull(getSupportActionBar()).setTitle(movieTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        layoutVisible(false);
        getData();
    }



    void getData() {
        MovieService movieService = APIClient.getClient()
                .create(MovieService.class);
        movieService.getMovie(movieId,BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Movie>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Movie movie) {
                        processData(movie);
                        Log.i("cek", "onSuccess: "+movie.getPosterPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("detail",e.getLocalizedMessage());
                    }
                });
    }

    void processData(Movie data) {
        genreList.clear();
        genreList.addAll(data.getGenres());
        layoutVisible(true);
        adapter.notifyDataSetChanged();
        initView(data);
//        adapter.notifyDataSetChanged();
    }

    void layoutVisible(Boolean status){
        ProgressBar loadingView = findViewById(R.id.movieDetailProggresbar);
        if (!status){
            loadingView.setVisibility(View.VISIBLE);
            movieName.setVisibility(View.GONE);
            movieScore.setVisibility(View.GONE);
            movieDuration.setVisibility(View.GONE);
            movieLang.setVisibility(View.GONE);
            movieOverview.setVisibility(View.GONE);
            movieRelease.setVisibility(View.GONE);
            posterImage.setVisibility(View.GONE);
        } else {
            loadingView.setVisibility(View.GONE);
            movieName.setVisibility(View.VISIBLE);
            movieScore.setVisibility(View.VISIBLE);
            movieDuration.setVisibility(View.VISIBLE);
            movieLang.setVisibility(View.VISIBLE);
            movieOverview.setVisibility(View.VISIBLE);
            movieRelease.setVisibility(View.VISIBLE);
            posterImage.setVisibility(View.VISIBLE);

        }
    }



    void initView(Movie movie){

        Glide.with(this)
                .load("https://image.tmdb.org/t/p/w185/"+ movie.getPosterPath())
                .into(posterImage);
        movieName.setText(movie.getOriginalTitle());
        movieScore.setText(String.valueOf(movie.getVoteAverage()));
        movieDuration.setText(String.valueOf(movie.getPopularity()));
        movieLang.setText(movie.getOriginalLanguage());
        movieOverview.setText(movie.getOverview());
        movieRelease.setText(movie.getReleaseDate());

    }
}
