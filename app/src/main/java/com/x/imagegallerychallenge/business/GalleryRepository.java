package com.x.imagegallerychallenge.business;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.x.imagegallerychallenge.models.Picture;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
                .baseUrl("https://api.imgflip.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        galleryApi = retrofit.create(GalleryApi.class);

        allPictures = galleryDao.getAllPicture();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return galleryDao.getAllPicture();
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

    public void fetchOnlinePictures(OnFetchPicturesListener onFetchPicturesListener) {
        Call<LinkedTreeMap<String, Object>> call = galleryApi.getPictures();
        call.enqueue(new Callback<LinkedTreeMap<String, Object>>() {
            @Override
            public void onResponse(Call<LinkedTreeMap<String, Object>> call, Response<LinkedTreeMap<String, Object>> response) {
                if (!response.isSuccessful()) {
                    Log.d("error", response.message());
                    onFetchPicturesListener.failed(null);
                    return;
                }
                try {
                    LinkedTreeMap<String, Object> responseBody = response.body();
                    boolean responseStatus = (boolean) responseBody.get("success");

                    if (!responseStatus) {
                        onFetchPicturesListener.failed(null);
                        return;
                    }
                    LinkedTreeMap<String, Object> data = (LinkedTreeMap<String, Object>) responseBody.get("data");
                    List<Object> picturesObjects = (List<Object>) data.get("memes");
                    List<Picture> pictureList = new ArrayList<>();
                    for (Object pictureObject : picturesObjects) {
                        pictureList.add(Picture.map(pictureObject));
                    }

                    for (Picture picture :
                            pictureList) {
                        insertPicture(picture);
                    }

                    onFetchPicturesListener.requestSucceeded(pictureList);

                    Log.d("Memes count", String.valueOf(pictureList.size()));

                } catch (Exception e) {
                    onFetchPicturesListener.failed(null);
                }


            }

            @Override
            public void onFailure(Call<LinkedTreeMap<String, Object>> call, Throwable t) {
                Log.d("error", t.getMessage());
                onFetchPicturesListener.failed(null);
            }
        });
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
