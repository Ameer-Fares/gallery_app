package com.x.imagegallerychallenge.adapters;

import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.x.imagegallerychallenge.R;
import com.x.imagegallerychallenge.models.Picture;
import com.x.imagegallerychallenge.ui.gallery.GalleryViewModel;

import org.jetbrains.annotations.NotNull;

public class GalleryAdapter extends ListAdapter<Picture, GalleryAdapter.GalleryHolder> {
    private AppCompatActivity context;
    private GalleryViewModel galleryViewModel;
    private GalleryAdapter.OnItemClickListener listener;

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
        if (getItem(position).getImage() != null)
            holder.itemImageView.setImageBitmap(BitmapFactory.decodeByteArray(getItem(position).getImage(), 0, getItem(position).getImage().length));
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
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Picture picture);
    }


    public void setOnItemClickListener(GalleryAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setGalleryViewModel(GalleryViewModel galleryViewModel) {
        this.galleryViewModel = galleryViewModel;
    }
}
