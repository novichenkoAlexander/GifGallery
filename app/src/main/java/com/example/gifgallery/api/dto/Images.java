package com.example.gifgallery.api.dto;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("original")
    private GifImage original;

    @SerializedName("fixed_width")
    private GifImage fixed_width;

    public GifImage getOriginal() {
        return original;
    }

    public GifImage getFixed_width() {
        return fixed_width;
    }
}
