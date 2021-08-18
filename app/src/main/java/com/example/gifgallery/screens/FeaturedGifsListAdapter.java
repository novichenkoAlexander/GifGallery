package com.example.gifgallery.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.Images;

public abstract class FeaturedGifsListAdapter extends ListAdapter<Gif, FeaturedGifsListAdapter.GifsItemViewHolder> {


    protected FeaturedGifsListAdapter() {
        super(new GifDiffUtil());
    }

    @NonNull
    @Override
    public GifsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GifsItemViewHolder((LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull GifsItemViewHolder holder, int position) {
        final Gif gif = getItem(position);
        if (gif == null) return;
        final Images images = gif.getImages();
        final String gifUrl = images.getFixed_width().getGifUrl();

        holder.bind(gifUrl);

        if (position == getItemCount() - 1) {
            loadMore();
        }
    }

    public abstract void loadMore();

    public static class GifsItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public GifsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivGif);
        }

        private void bind(String url) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }

    static class GifDiffUtil extends DiffUtil.ItemCallback<Gif> {

        @Override
        public boolean areItemsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Gif oldItem, @NonNull Gif newItem) {
            return oldItem.getId().equals(newItem.getId());
        }
    }
}
