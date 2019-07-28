package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {

    private final static String API_KEY = "c78f658f";

    EditText mEditText;
    Button mSubmit;
    String query;
   // Movie m;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mSubmit = findViewById(R.id.submit);
        mEditText = findViewById(R.id.editText);
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(API_KEY, "Twilight");
        call.enqueue(new Callback<Movie>() {

            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie m = response.body();
            }

            @Override
            public void onFailure(Call<Movie>call, Throwable t) {
                Toast.makeText(SearchActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                query = mEditText.getText().toString();
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                Call<Movie> call = apiService.getMovieDetails(API_KEY, query);
                call.enqueue(new Callback<Movie>() {

                    @Override
                    public void onResponse(Call<Movie> call, Response<Movie> response) {
                        Movie m = response.body();
                        Intent myIntent = new Intent(SearchActivity.this,
                                NewActivity.class);
                        myIntent.putExtra("title", m.getTitle());
                        myIntent.putExtra("plot", m.getPlot());
                        myIntent.putExtra("actors", m.getActors());
                        myIntent.putExtra("year", m.getYear());
                        myIntent.putExtra("genre", m.getGenre());
                        myIntent.putExtra("director", m.getDirector());
                        myIntent.putExtra("imdb", m.getImdbRating());
                        myIntent.putExtra("poster", m.getPoster());
                        startActivity(myIntent);
                    }

                    @Override
                    public void onFailure(Call<Movie>call, Throwable t) {
                        Toast.makeText(SearchActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
                    }
                });


            }
        });
    }
}
