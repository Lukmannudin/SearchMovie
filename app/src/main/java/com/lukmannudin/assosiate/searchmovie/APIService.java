package com.lukmannudin.assosiate.searchmovie;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("trending/movie/week")
    Single<Response<Movie>> getMovieTrending(@Query("api_key") String api_key);

    @GET("search/movie")
    Single<Response<Movie>> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query
    );

}
