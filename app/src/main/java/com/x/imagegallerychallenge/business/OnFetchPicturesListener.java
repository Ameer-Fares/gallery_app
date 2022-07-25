package com.x.imagegallerychallenge.business;

import com.x.imagegallerychallenge.models.Picture;

import java.util.List;

public interface OnFetchPicturesListener extends Failable{
    void requestSucceeded(List<Picture> pictureList);
}
