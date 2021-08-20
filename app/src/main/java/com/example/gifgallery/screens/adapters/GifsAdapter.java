package com.example.gifgallery.screens.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.Images;

import java.util.List;

public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ItemViewHolder> {


    private final List<Gif> gifs;
    private OnLoadMoreListener loadMoreListener;

    public GifsAdapter(List<Gif> gifs) {
        this.gifs = gifs;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final Gif gif = gifs.get(position);
        if (gif == null) return;
        final Images images = gif.getImages();
        final String gifUrl = images.getFixed_width().getGifUrl();

        if (position == getItemCount() - 1) {
            loadMoreListener.onLoadMore();
        }
        holder.bind(gifUrl);
    }

    public void addGifs(List<Gif> gifList) {
        gifs.addAll(gifList);
        notifyItemInserted(getItemCount() - 1);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }


    @Override
    public int getItemCount() {
        return gifs.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.ivGif);
        }

        void bind(String url) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}



