package com.example.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.ModelAdapter;

import java.util.List;


public class NewActivity extends AppCompatActivity {

    private final static String API_KEY = "c78f658f";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        ActionBar actionBar = getSupportActionBar();
        TextView mTitle = findViewById(R.id.textTitle);
        TextView mPLot = findViewById(R.id.textPlot);
        TextView mActors = findViewById(R.id.textActors);
        TextView mYear = findViewById(R.id.textYear);
        TextView mGenre = findViewById(R.id.textGenre);
        TextView mDirector = findViewById(R.id.textDirector);
        TextView mImdb = findViewById(R.id.textImdb);
        ImageView mPoster = findViewById(R.id.imageDisplay);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String plot = intent.getStringExtra("plot");
        String actors = intent.getStringExtra("actors");
        String year = intent.getStringExtra("year");
        String genre = intent.getStringExtra("genre");
        String director = intent.getStringExtra("director");
        String imdb = intent.getStringExtra("imdb");
        String poster = intent.getStringExtra("poster");

        List<MovieModel> movieModelList = SQLite.select().
                from(MovieModel.class).queryList();
        int length = movieModelList.size();
        String id = Integer.toString(length+1);

        MovieModel movieModel = new MovieModel();

        movieModel.setId_db(id);
        movieModel.setTitle_db(title);
        movieModel.setPlot_db(plot);
        movieModel.setActors_db(actors);
        movieModel.setYear_db(year);
        movieModel.setGenre_db(genre);
        movieModel.setDirector_db(director);
        movieModel.setImdb_db(imdb);
        movieModel.setPoster_db(poster);
        movieModel.insert();

        List<MovieModel> movieList = SQLite.select().
                from(MovieModel.class).queryList();
        Log.d("NewActivity","Database " + movieList.get(0).getTitle_db() + " " +length);

        actionBar.setTitle(title);
        mTitle.setText(title.toUpperCase());
        mPLot.setText(plot);
        mActors.setText(actors.toUpperCase());
        mYear.setText(year);
        mGenre.setText(genre);
        mDirector.setText(director);
        mImdb.setText(imdb);


    }
}
