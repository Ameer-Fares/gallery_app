package com.x.imagegallerychallenge.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users_table")
public class User {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    private String username;
    private String password;

    @NonNull
    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(@NonNull String username, String password) {
        this.username = username;
        this.password = password;
    }
}
