package com.example.gifgallery.screens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;

import java.util.List;

public class FeaturedGifsAdapter extends RecyclerView.Adapter<FeaturedGifsAdapter.ViewHolder> {

    private List<String> gifsUrls;

    public FeaturedGifsAdapter(List<String> gifsUrls) {
        this.gifsUrls = gifsUrls;
    }

    public void submitList(List<String> list) {
        this.gifsUrls = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder((LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gif_list_item, parent, false)));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String gifUrl = gifsUrls.get(position);
        holder.bind(gifUrl);
    }

    @Override
    public int getItemCount() {
        return (gifsUrls == null) ? 0 : gifsUrls.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.ivGif);
        }

        private void bind(String url) {
            Glide.with(itemView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}
