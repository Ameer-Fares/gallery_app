package com.x.imagegallerychallenge.ui.imageview;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.models.Picture;

public class ImageFragment extends DialogFragment {
    Picture picture;

    public ImageFragment(Picture picture) {
        this.picture = picture;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment_layout, container, false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TouchImageView imageView = view.findViewById(R.id.image_view);
        imageView.setImageBitmap(BitmapFactory.decodeByteArray(picture.getImage(), 0, picture.getImage().length));

    }
}
