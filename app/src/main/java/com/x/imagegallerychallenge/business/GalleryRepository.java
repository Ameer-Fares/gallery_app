package com.x.imagegallerychallenge.business;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.gson.JsonIOException;
import com.google.gson.internal.LinkedTreeMap;
import com.x.imagegallerychallenge.models.Picture;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
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
        new ManagePicturesAsync().insertPicture(galleryDao, picture);
    }

    public void updatePicture(Picture picture) {
        new ManagePicturesAsync().updatePicture(galleryDao, picture);
    }

    public void deleteAllPicture() {
        new ManagePicturesAsync().deleteAllPicture(galleryDao);
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
                        Picture picture = Picture.map(pictureObject);
                        pictureList.add(picture);
                        insertPicture(picture);
                        if (picture != null)
                            fetchImageData(picture);
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

    public void fetchImageData(Picture picture) {
        Call<ResponseBody> call = galleryApi.getImageData(picture.getUrl());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Log.d("error", response.message());
                    return;
                }
                try {
                    InputStream inputStream = response.body().byteStream();
                    picture.setImage(HelperMethods.getBytesFromInputStream(inputStream));
                    updatePicture(picture);
                } catch (Exception e) {
                    Log.v("fetch image error", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("error", t.getMessage());
            }
        });
    }



    private static class ManagePicturesAsync {
        private void insertPicture(GalleryDao galleryDao, Picture picture) {
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

        private void updatePicture(GalleryDao galleryDao, Picture picture) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.updatePicture(picture);
                }
            }).start();
        }

        private void deletePicture(GalleryDao galleryDao, Picture picture) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.deletePicture(picture);
                }
            }).start();
        }

        private void deleteAllPicture(GalleryDao galleryDao) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    galleryDao.deleteAllPicture();
                }
            }).start();
        }
    }
}
