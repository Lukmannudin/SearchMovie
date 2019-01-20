package com.lukmannudin.assosiate.searchmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.lukmannudin.assosiate.searchmovie.APIRequest.MovieService;
import com.lukmannudin.assosiate.searchmovie.Model.MovieTrending;
import com.lukmannudin.assosiate.searchmovie.Response.Response;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private List<MovieTrending> movieTrendingList = new ArrayList<>();
    private RecyclerView movieRecyclerView;
    private MovieAdapter adapter;
    private Button btnSearch;
    private EditText edtSearch, src;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        movieRecyclerView = findViewById(R.id.rvMovie);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearchText);
        loading = findViewById(R.id.mainProggressbar);
        getData();
        adapter = new MovieAdapter(movieTrendingList, itemlistener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(adapter);

        edtSearch.clearFocus();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String queryData = edtSearch.getText().toString();
                Log.i("querySearch", queryData);
                searchData(queryData);
            }
        });

    }

    void getData() {
        loading.setVisibility(View.VISIBLE);
        MovieService movieService = APIClient.getClient()
                .create(MovieService.class);
        movieService.getMovieTrending(BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MovieTrending>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<MovieTrending> movieResponse) {
                        processData(movieResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    void searchData(String queryData) {
        loading.setVisibility(View.VISIBLE);
        MovieService movieService = APIClient.getClient()
                .create(MovieService.class);
        movieService.searchMovie(BuildConfig.API_KEY, queryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<MovieTrending>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<MovieTrending> movieResponse) {
                        processData(movieResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
//        loading.setVisibility(View.GONE);
    }

    void processData(List<MovieTrending> data) {
        movieTrendingList.clear();
        movieTrendingList.addAll(data);
        loading.setVisibility(View.GONE);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startIntent(String movieId, String movieName) {
        Intent intent = new Intent(this, MovieDetail.class);
        intent.putExtra("movieId", movieId);
        intent.putExtra("movieTitle", movieName);
        startActivity(intent);
    }

    private itemClickListener itemlistener = new itemClickListener() {
        @Override
        public void openDetailMovie(String movieId, String movieName) {
            startIntent(movieId, movieName);
        }
    };

    interface itemClickListener {
        void openDetailMovie(String movieId, String movieName);
    }
}