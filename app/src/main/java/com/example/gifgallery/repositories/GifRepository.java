package com.example.gifgallery.repositories;


import com.example.gifgallery.api.GiphyApiInterface;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.network.GiphyApiFactory;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GifRepository {

    private final GiphyApiInterface apiInterface;

    public GifRepository() {
        apiInterface = GiphyApiFactory.getInstance().getNetworkClient();
    }

    public Observable<List<Gif>> getTrendingGifsResponseObservable(int limit, int offset) {
        return apiInterface.getTrendingGifs(GiphyApiFactory.API_KEY,
                limit, offset)
                .map(result -> Observable.fromIterable(result.getListOfGifs()))
                .flatMap(x -> x)
                .toList().toObservable();
    }

    public Observable<List<Gif>> getSearchObservable(String query, int limit, int offset) {
        return apiInterface.searchGifs(GiphyApiFactory.API_KEY, query, limit, offset)
                .map(searchResult -> Observable.fromIterable(searchResult.getListOfGifs()))
                .flatMap(x -> x)
                .toList().toObservable();
    }
}
