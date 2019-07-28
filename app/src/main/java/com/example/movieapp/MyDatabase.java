package com.example.movieapp;

import com.raizlabs.android.dbflow.annotation.Database;

@Database(name = MyDatabase.NAME, version = MyDatabase.VERSION)
public class MyDatabase {
    public static final String NAME = "MovieDatabase";

    public static final int VERSION = 1;
}
