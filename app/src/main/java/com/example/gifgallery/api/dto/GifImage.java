package com.example.gifgallery.api.dto;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "gifs")
public class GifImage {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "url")
    @SerializedName("url")
    private String gifUrl;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGifUrl() {
        return gifUrl;
    }

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setGifUrl(@NonNull String gifUrl) {
        this.gifUrl = gifUrl;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
