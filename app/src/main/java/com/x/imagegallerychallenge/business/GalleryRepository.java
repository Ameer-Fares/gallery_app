package com.x.imagegallerychallenge.business;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.x.imagegallerychallenge.models.Picture;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GalleryRepository {
    private GalleryDao galleryDao;
    private LiveData<List<Picture>> allPictures;
    private GalleryApi galleryApi;
    private Retrofit retrofit;

    public GalleryRepository(Application application) {
        GalleryDatabase database = GalleryDatabase.getInstance(application);
        galleryDao = database.galleryDao();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://ireadkashkoul.com/wp-json/wp/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        galleryApi = retrofit.create(GalleryApi.class);

        allPictures = galleryDao.getAllPicture();
    }

    public void insertPicture(Picture picture) {
        new ManagePicturesAsync().insertPost(galleryDao, picture);
    }

    public void updatePost(Picture picture) {
        new ManagePicturesAsync().updatePost(galleryDao, picture);
    }

    public void deleteAllPosts() {
        new ManagePicturesAsync().deleteAllPosts(galleryDao);
    }


    private static class ManagePicturesAsync {
        private void insertPost(GalleryDao galleryDao, Picture picture) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        galleryDao.insertPicture(picture);
                    } catch (SQLiteConstraintException e) {
                        Log.d("error", e.getMessage());
                    }
                }
            }).start();
        }

        private void updatePost(GalleryDao galleryDao, Picture picture) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.updatePicture(picture);
                }
            }).start();
        }

        private void deletePost(GalleryDao galleryDao, Picture picture) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.deletePicture(picture);
                }
            }).start();
        }

        private void deleteAllPosts(GalleryDao galleryDao) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.deleteAllPicture();
                }
            }).start();
        }
    }
}
