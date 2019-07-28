package com.example.movieapp;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = MyDatabase.class, name = "tblMovie")
public class MovieModel extends BaseModel {

    @PrimaryKey
    @Column
    String id;

    @PrimaryKey
    @Column
    String title;

    @Column
    String plot;

    @Column
    String actors;

    @Column
    String year;

    @Column
    String genre;

    @Column
    String director;

    @Column
    String poster;

    @Column
    String imdb;

    public String getId_db() { return id;}

    public String getTitle_db() {
        return title;
    }

    public String getPlot_db() { return plot; }

    public String getActors_db() {
        return actors;
    }

    public String getYear_db() {
        return year;
    }

    public String getGenre_db() {
        return genre;
    }

    public String getDirector_db() {
        return director;
    }

    public String getPoster_db() { return poster;}

    public String getImdb_db() { return imdb; }


    public void setId_db(String id) { this.id = id;}

    public void setTitle_db(String title) { this.title = title; }

    public void setPlot_db(String plot) { this.plot =  plot; }

    public void setActors_db(String actors) { this.actors = actors; }

    public void setYear_db(String year) { this.year = year; }

    public void setGenre_db(String genre) { this.genre = genre; }

    public void setDirector_db(String director) { this.director = director; }

    public void setPoster_db(String poster) { this.poster = poster;}

    public void setImdb_db(String imdb) { this.imdb = imdb; }

}
