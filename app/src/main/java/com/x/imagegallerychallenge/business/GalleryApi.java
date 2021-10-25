package com.x.imagegallerychallenge.business;

import com.google.gson.internal.LinkedTreeMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GalleryApi {
    @GET("posts")
    Call<List<LinkedTreeMap<String, Object>>> getPictures();
}
