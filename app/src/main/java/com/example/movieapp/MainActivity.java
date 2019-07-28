package com.example.movieapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    String query = "";
    ListView listView;
    ListViewAdapter adapter;
    Movie m;

    private final static String API_KEY = "c78f658f";

    String mTitle[] = { "Harry Potter", "Stranger Things", "Gangs of Wasseypur" , "The Da Vinci Code"};
    String mDesccription[] = {"The Adventure Begins", "The Mystery Unveils", "It's always the unexpected", "Can you Decode it?"};
    int image[] = {R.drawable.harry_potter, R.drawable.stranger_things, R.drawable.gangs, R.drawable.da_vinci_code};



    ArrayList<Model> arrayList = new ArrayList<Model>();

    @Override
    protected void onCreate(Bundle savedInstabceState){

        super.onCreate(savedInstabceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Movie Search");

        listView = findViewById(R.id.listView);

       /* for (int i = 0; i < mTitle.length; i++) {

            Model model = new Model(mTitle[i], mDesccription[i], image[i]);
            arrayList.add(model);
        } */

        List<MovieModel> movieModelList = SQLite.select().
                from(MovieModel.class).queryList();

        for(MovieModel m : movieModelList) {
            Model model = new Model(m.getTitle_db(), m.getPlot_db(), R.drawable.movieicon);
            arrayList.add(model);
        }

        adapter = new ListViewAdapter(this, arrayList);

        listView.setAdapter(adapter);

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
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView)myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent = new Intent(getApplicationContext(), NewActivity.class);
                intent.putExtra("actionBarTitle", s);
                intent.putExtra("content", "");
                getApplicationContext().startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (TextUtils.isEmpty(s)){
                    adapter.filter("");
                    listView.clearTextFilter();
                }
                else {
                    adapter.filter(s);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.action_settings){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    Movie getQuery(String query) {
        Movie m = null;
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(API_KEY, query);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                Movie m = response.body();
            }

            @Override
            public void onFailure(Call<Movie>call, Throwable t) {
                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT);
            }
        });
        return m;
    }
}
