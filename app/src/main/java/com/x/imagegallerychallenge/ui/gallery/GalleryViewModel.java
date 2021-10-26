package com.x.imagegallerychallenge.ui.gallery;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.x.imagegallerychallenge.business.GalleryRepository;
import com.x.imagegallerychallenge.business.OnFetchPicturesListener;
import com.x.imagegallerychallenge.models.Picture;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {
    private LiveData<List<Picture>> allPictures;
    private GalleryRepository galleryRepository;

    public GalleryViewModel(@NotNull Application application) {
        super(application);
        galleryRepository = new GalleryRepository(application);
        allPictures = galleryRepository.getAllPictures();
    }

    public LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    public void fetchOnlinePictures(OnFetchPicturesListener onFetchPicturesListener) {
        galleryRepository.fetchOnlinePictures(onFetchPicturesListener);
    }

    public void fetchImageData(Picture picture){
        galleryRepository.fetchImageData(picture);
    }

    public void updateImageInfo(Picture picture){
        galleryRepository.updatePicture(picture);
    }
}
