package com.example.movieapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListViewAdapter extends BaseAdapter {

    Movie m;
    Context mContext;
    LayoutInflater inflater;
    List<Model> modellist;
    ArrayList<Model> arrayList;
    private final static String API_KEY = "c78f658f";
    //constructor
    public ListViewAdapter(Context context, List<Model> modellist) {
        mContext = context;
        this.modellist = modellist;
        inflater = LayoutInflater.from(mContext);
        this.arrayList = new ArrayList<Model>();
        this.arrayList.addAll(modellist);
    }

    public class ViewHolder {
        TextView mTitle, mDesc;
        ImageView mImg;
    }

    @Override
    public int getCount() {
        return modellist.size();
    }

    @Override
    public Object getItem(int i) {
        return modellist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view==null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.row, null);

            //locate the views in row.xml
            holder.mTitle = view.findViewById(R.id.textView1);
            holder.mDesc = view.findViewById(R.id.textView2);
            holder.mImg = view.findViewById(R.id.image);

            view.setTag(holder);

        }
        else {
            holder = (ViewHolder)view.getTag();
        }

        holder.mTitle.setText(modellist.get(position).getTitle());
        holder.mDesc.setText(modellist.get(position).getDesc());
        holder.mImg.setImageResource(modellist.get(position).getIcon());


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                if (modellist.get(position).getTitle().equals("Harry Potter")){

                    Intent intent = new Intent(mContext, NewActivity.class);
                    intent.putExtra("title", "Harry Potter");
                    intent.putExtra("content", "The Adventure Begins");
                    mContext.startActivity(intent);

                }
                if (modellist.get(position).getTitle().equals("Stranger Things")){

                    Intent intent = new Intent(mContext, NewActivity.class);
                    intent.putExtra("title", "Stranger Things");
                    intent.putExtra("plot", "The Mystery Unveils");
                    mContext.startActivity(intent);

                }
                if (modellist.get(position).getTitle().equals("Gangs of Wasseypur")){

                    Intent intent = new Intent(mContext, NewActivity.class);
                    intent.putExtra("title", "Gangs of Wassepur");
                    intent.putExtra("plot", "It's always the unexpected");
                    mContext.startActivity(intent);

                }
                if (modellist.get(position).getTitle().equals("The Da Vinci Code")){

                    Intent intent = new Intent(mContext, NewActivity.class);
                    intent.putExtra("title", "The Da Vinci Code");
                    intent.putExtra("plot", "Can you Decode it?");
                    mContext.startActivity(intent);

                } */
                String mTitle = modellist.get(position).getTitle();
                List<MovieModel> movieModelList = SQLite.select().
                        from(MovieModel.class).queryList();
                for(MovieModel m : movieModelList){
                    if (m.getTitle_db().equals( mTitle)) {
                        Intent intent = new Intent(mContext, NewActivity.class);
                        intent.putExtra("title", m.getTitle_db());
                        intent.putExtra("plot", m.getPlot_db());
                        intent.putExtra("actors", m.getActors_db());
                        intent.putExtra("year", m.getYear_db());
                        intent.putExtra("genre", m.getGenre_db());
                        intent.putExtra("director", m.getDirector_db());
                        intent.putExtra("imdb", m.getImdb_db());
                        intent.putExtra("poster", m.getPoster_db());
                        mContext.startActivity(intent);
                    }
                }
            }
        });

        return view;
    }
    public void filter(String charText){
        charText = charText.toLowerCase(Locale.getDefault());
        modellist.clear();
        if (charText.length()==0){
            modellist.addAll(arrayList);
        }
        else {
            for (Model model : arrayList){
                if (model.getTitle().toLowerCase(Locale.getDefault())
                        .contains(charText)){
                    modellist.add(model);
                }
                if(modellist.isEmpty()) {
                    processQuery(charText);

                }
            }

        }
        notifyDataSetChanged();
    }
    public void processQuery(String s) {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        Call<Movie> call = apiService.getMovieDetails(API_KEY, s);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                //   List<Movie> movies = response.body().
                m = response.body();
            }

            @Override
            public void onFailure(Call<Movie>call, Throwable t) {
                // Toast.makeText(, "Something went wrong", Toast.LENGTH_SHORT);
            }
        });
    }
}
