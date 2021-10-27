package com.x.imagegallerychallenge.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class User {
    @PrimaryKey(autoGenerate = false)
    private String username;
    private String password;
}
