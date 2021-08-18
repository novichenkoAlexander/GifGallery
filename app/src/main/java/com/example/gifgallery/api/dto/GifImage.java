package com.example.gifgallery.api.dto;

import com.google.gson.annotations.SerializedName;

public class GifImage {

    @SerializedName("url")
    private String gifUrl;

    public String getGifUrl() {
        return gifUrl;
    }
}
