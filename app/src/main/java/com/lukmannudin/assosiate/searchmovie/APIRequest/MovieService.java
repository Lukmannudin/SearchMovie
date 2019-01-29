package com.lukmannudin.assosiate.searchmovie.APIRequest;


import com.lukmannudin.assosiate.searchmovie.Model.Movie;
import com.lukmannudin.assosiate.searchmovie.Model.MovieTrending;
import com.lukmannudin.assosiate.searchmovie.Response.Response;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("trending/movie/week")
    Single<Response<MovieTrending>> getMovieTrending(@Query("api_key") String api_key);

    @GET("search/movie")
    Single<Response<MovieTrending>> searchMovie(
            @Query("api_key") String api_key,
            @Query("query") String query
    );

    @GET("movie/{movieId}")
    Single<Movie> getMovie(
            @Path("movieId") String movieId,
            @Query("api_key") String api_key
    );

}
