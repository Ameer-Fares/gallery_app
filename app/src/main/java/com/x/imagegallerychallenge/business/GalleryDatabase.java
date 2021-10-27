package com.x.imagegallerychallenge.business;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.x.imagegallerychallenge.models.Picture;
import com.x.imagegallerychallenge.models.User;

@Database(entities = {Picture.class, User.class}, version = 1)
public abstract class GalleryDatabase extends RoomDatabase {

    private static GalleryDatabase instance;

    public abstract GalleryDao galleryDao();

    public static synchronized GalleryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GalleryDatabase.class, "gallery_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}