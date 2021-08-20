package com.example.gifgallery.api;

import com.example.gifgallery.api.dto.PaginationResponse;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GiphyApiInterface {

    @GET("trending")
    Observable<PaginationResponse> getTrendingGifs(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

    @GET("search")
    Observable<PaginationResponse> searchGifs(
            @Query("api_key") String apiKey,
            @Query("q") String query,
            @Query("limit") int limit,
            @Query("offset") int offset
    );

}
