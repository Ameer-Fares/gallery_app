package com.x.imagegallerychallenge.business;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.x.imagegallerychallenge.models.Picture;

import java.util.List;

public interface GalleryDao {
    //region Images APIs
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertPicture(Picture picture);

    @Update
    void updatePicture(Picture picture);

    @Delete
    void deletePicture(Picture picture);

    @Query("DELETE FROM pictures_table")
    void deleteAllPicture();

    @Query("SELECT * FROM pictures_table ORDER BY id DESC")
    LiveData<List<Picture>> getAllPicture();
    //endregion
}