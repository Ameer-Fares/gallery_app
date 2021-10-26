package com.x.imagegallerychallenge.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.adapters.GalleryAdapter;
import com.x.imagegallerychallenge.business.OnFetchPicturesListener;
import com.x.imagegallerychallenge.databinding.FragmentGalleryBinding;
import com.x.imagegallerychallenge.models.Picture;

import java.util.HashMap;
import java.util.List;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private GalleryViewModel galleryViewModel;
    private GalleryAdapter galleryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);
        RecyclerView galleryRecyclerview = binding.galleryRecyclerview;
        galleryAdapter = new GalleryAdapter();
        galleryAdapter.setGalleryViewModel(galleryViewModel);
        galleryRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        galleryRecyclerview.setHasFixedSize(false);
        galleryRecyclerview.setAdapter(galleryAdapter);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        galleryViewModel.getAllPictures().observe(getViewLifecycleOwner(), new Observer<List<Picture>>() {
            @Override
            public void onChanged(List<Picture> pictures) {
                galleryAdapter.submitList(pictures);
                galleryAdapter.notifyDataSetChanged();
                galleryViewModel.getAllPictures().removeObservers(getViewLifecycleOwner());
            }
        });
        OnFetchPicturesListener onFetchPicturesListener = createPicturesListener();
        galleryViewModel.fetchOnlinePictures(onFetchPicturesListener);


//        binding.buttonFirst.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(GalleryFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

    private OnFetchPicturesListener createPicturesListener() {
        return new OnFetchPicturesListener() {
            @Override
            public void requestSucceeded(List<Picture> pictureList) {
                galleryAdapter.submitList(pictureList);
                galleryAdapter.notifyDataSetChanged();
            }

            @Override
            public void failed(HashMap<String, String> extraInfo) {
            }
        };
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}