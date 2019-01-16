package com.lukmannudin.assosiate.searchmovie;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("trending/movie/week")
    Observable<Response<Movie>> getMovieTrending(@Query("api_key") String api_key);
}
