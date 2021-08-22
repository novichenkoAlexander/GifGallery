package com.example.gifgallery.api.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaginationResponse {

    @SerializedName("data")
    private List<Gif> listOfGifs;

    @SerializedName("pagination")
    private Pagination pagination;

    public List<Gif> getListOfGifs() {
        return listOfGifs;
    }

    public Pagination getPagination() {
        return pagination;
    }
}
