package com.x.imagegallerychallenge.models;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.internal.LinkedTreeMap;

import java.util.Map;

@Entity(tableName = "pictures_table")
public class Picture {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String name;
    private String url;
    private double width;
    private double height;
    private int box_count;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;


    public static Picture map(Object obj) {
        try {
            Picture picture = new Picture();
            Map<String, Object> objectMap = (Map<String, Object>) obj;
            picture.setId(Integer.parseInt(objectMap.get("id").toString()));
            picture.setName(objectMap.get("name").toString());
            picture.setUrl(objectMap.get("url").toString());
            picture.setWidth(Double.parseDouble(objectMap.get("width").toString()));
            picture.setHeight(Double.parseDouble(objectMap.get("height").toString()));
            picture.setBox_count(Integer.parseInt(objectMap.get("box_count").toString().replace(".0","")));
            return picture;

        } catch (Exception e) {
            Log.d("error", e.getMessage());
            return null;
        }
    }


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

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getBox_count() {
        return box_count;
    }

    public void setBox_count(int box_count) {
        this.box_count = box_count;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
