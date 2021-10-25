package com.x.imagegallerychallenge.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pictures_table")
public class Picture {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String url;
    private int width;
    private int height;
    private int box_count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getBox_count() {
        return box_count;
    }

    public void setBox_count(int box_count) {
        this.box_count = box_count;
    }
}
