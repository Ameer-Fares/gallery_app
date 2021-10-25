package com.x.imagegallerychallenge.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.models.Picture;

import org.jetbrains.annotations.NotNull;

public class GalleryAdapter extends ListAdapter<Picture, GalleryAdapter.GalleryHolder> {
    private AppCompatActivity context;


    public GalleryAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Picture> DIFF_CALLBACK = new DiffUtil.ItemCallback<Picture>() {
        @Override
        public boolean areItemsTheSame(@NonNull @NotNull Picture oldItem, @NonNull @NotNull Picture newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull @NotNull Picture oldItem, @NonNull @NotNull Picture newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    };


    @NonNull
    @NotNull
    @Override
    public GalleryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        context = (AppCompatActivity) parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_item_card, parent, false);

        return new GalleryAdapter.GalleryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull GalleryHolder holder, int position) {
        Picture picture = getItem(position);


    }

    class GalleryHolder extends RecyclerView.ViewHolder {
        private final ImageView itemImageView;

        public GalleryHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemImageView = itemView.findViewById(R.id.item_imageview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(getItem(position));
//                    }
                }
            });
        }
    }


}
