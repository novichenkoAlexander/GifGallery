package com.example.gifgallery.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;
import com.example.gifgallery.api.dto.GifImage;

import java.util.List;

public class FavoriteGifsAdapter extends RecyclerView.Adapter<FavoriteGifsAdapter.FavoritesViewHolder> {

    private final List<GifImage> gifImages;

    public FavoriteGifsAdapter(List<GifImage> gifImages) {
        this.gifImages = gifImages;
    }

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoritesViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, int position) {
        holder.bind(gifImages.get(position).getGifUrl());
    }

    @Override
    public int getItemCount() {
        return gifImages.size();
    }

    static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public FavoritesViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivGif);
        }

        void bind(String url) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_loading)
                    .into(imageView);
        }
    }

}
