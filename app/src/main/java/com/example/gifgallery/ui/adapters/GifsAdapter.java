package com.example.gifgallery.ui.adapters;


import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gifgallery.R;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.api.dto.Images;

import java.util.List;
import java.util.function.Consumer;

public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.ItemViewHolder> {

    private final List<Gif> gifs;
    private final Runnable runnable;
    private final Consumer<Integer> consumer;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public GifsAdapter(List<Gif> gifs, Runnable runnable, Consumer<Integer> consumer) {
        this.gifs = gifs;
        this.runnable = runnable;
        this.consumer = consumer;
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

        if (position > 0 && (position == getItemCount() - 1)) {
            runnable.run();
        }
        holder.bind(gifUrl);
    }

    public void addGifs(List<Gif> gifList) {
        gifs.addAll(gifList);
        notifyItemInserted(getItemCount() - 1);
    }

    public GifImage getGifImageByPosition(int position) {
        return gifs.get(position).getImages().getFixed_width();
    }


    @Override
    public int getItemCount() {
        return gifs.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                consumer.accept(this.getAdapterPosition());
            }
            Toast.makeText(v.getContext(), "Added to Favorite", Toast.LENGTH_SHORT).show();
            return true;
        }
    }
}



