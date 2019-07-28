package com.example.movieapp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/")
    Call<Movie>getMovieDetails(@Query("apikey") String apiKey,@Query("t") String name);

}