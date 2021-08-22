package com.example.gifgallery.ui.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.api.dto.Images;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ItemViewHolder> {

    private final List<Gif> gifs;
    private OnLoadMoreListener loadMoreListener;
    private static OnLongItemClickListener longItemClickListener;

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

    public interface OnLongItemClickListener {
        void onLongItemClick(View v, int position);
    }

    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        GifsAdapter.longItemClickListener = longItemClickListener;
    }

    public GifImage getGifImageByPosition(int position) {
        return gifs.get(position).getImages().getFixed_width();
    }


    @Override
    public int getItemCount() {
        return gifs.size();
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        private final ImageView gifImageView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            gifImageView = itemView.findViewById(R.id.ivGif);
        }

        void bind(String url) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .placeholder(R.drawable.ic_loading)
                    .into(gifImageView);
        }

        @Override
        public boolean onLongClick(View v) {
            longItemClickListener.onLongItemClick(v, this.getAdapterPosition());
            Snackbar.make(v, "Added to Favorite", Snackbar.LENGTH_SHORT).show();
            return true;
        }
    }
}



