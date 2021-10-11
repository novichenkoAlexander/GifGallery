package com.example.gifgallery.api.dto;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;


@Entity(tableName = "gifs")
public class GifImage {

//    @ColumnInfo(name = "id")
//    private long id;

    @PrimaryKey
    @ColumnInfo(name = "url")
    @SerializedName("url")
    @NonNull
    private String gifUrl;

    @ColumnInfo(name = "isFavorite")
    private boolean isFavorite;

//    public long getId() {
//        return id;
//    }

//    public void setId(long id) {
//        this.id = id;
//    }

    private boolean isDeleted;

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
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
