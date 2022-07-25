package com.x.imagegallerychallenge.business;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.x.imagegallerychallenge.models.Picture;
import com.x.imagegallerychallenge.models.User;

import java.util.List;

@Dao
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

    // region User APIs
    @Insert(onConflict = OnConflictStrategy.ABORT)
    void insertUser(User user);


    @Query("SELECT * FROM users_table WHERE username = :username")
    LiveData<User> getUsers(String username);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);


    @Query("SELECT * FROM users_table WHERE username LIKE :username")
    LiveData<List<User>> getUserbyUsername(String username);
    //endregion
}