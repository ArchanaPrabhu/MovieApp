package com.example.movieapp;

public class Model {
    String title;
    String desc;
    int img;
    public Model(String title, String desc, int icon) {
        this.title = title;
        this.desc = desc;
        this.img = icon;
    }
    public String getTitle() {
        return this.title;
    }
    public String getDesc() {
        return this.desc;
    }
    public int getIcon() {
        return this.img;
    }
}
