package com.x.imagegallerychallenge.business;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface GalleryApi {
    @GET("get_memes")
    Call<LinkedTreeMap<String, Object>> getPictures();

    @GET()
    Call<ResponseBody> getImageData(@Url String url);
}
