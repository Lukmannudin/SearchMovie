package com.lukmannudin.assosiate.searchmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private List<Movie> movieList = new ArrayList<>();
    private RecyclerView movieRecyclerView;
    private MovieAdapter adapter;
    private Button btnSearch;
    private EditText edtSearch, src;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getData();

        movieRecyclerView = findViewById(R.id.rvMovie);
        btnSearch = findViewById(R.id.btnSearch);
        edtSearch = findViewById(R.id.edtSearchText);

        adapter = new MovieAdapter(movieList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setAdapter(adapter);

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
        APIService apiService = APIClient.getClient()
                .create(APIService.class);
        apiService.getMovieTrending(BuildConfig.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Movie> movieResponse) {
                        processData(movieResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }


    void searchData(String queryData) {
        Log.i("search", queryData);
        APIService apiService = APIClient.getClient()
                .create(APIService.class);
        apiService.searchMovie(BuildConfig.API_KEY, queryData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Response<Movie> movieResponse) {
                        Log.i("search berhasil", movieResponse.getResults().get(0).getTitle());
                        processData(movieResponse.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    void processData(List<Movie> data) {
        movieList.clear();
        movieList.addAll(data);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }
}