package com.example.gifgallery.screens.adapters;


import android.content.Context;
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
    private final Context context;
    private OnLoadMoreListener loadMoreListener;

    public GifsAdapter(List<Gif> gifs, Context context) {
        this.gifs = gifs;
        this.context = context;
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

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private final ImageView gifImageView;
        private final ImageView favoriteFlagImageView;
        private int clickCounter = 0;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            gifImageView = itemView.findViewById(R.id.ivGif);
            favoriteFlagImageView = itemView.findViewById(R.id.ivFavorite);
        }

        void bind(String url) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_loading)
                    .into(gifImageView);
        }

        @Override
        public boolean onLongClick(View v) {
            if (clickCounter == 0) {
                favoriteFlagImageView.setVisibility(View.VISIBLE);
                clickCounter++;
            } else if (clickCounter == 1) {
                favoriteFlagImageView.setVisibility(View.GONE);
                clickCounter = 0;
            }
            return true;
        }
    }
}



