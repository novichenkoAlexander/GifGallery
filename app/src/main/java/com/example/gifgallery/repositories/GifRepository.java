package com.example.gifgallery.repositories;


import com.example.gifgallery.api.GiphyApiInterface;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.network.NetworkService;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class GifRepository {

    private GiphyApiInterface apiInterface;
    private Observable<List<Gif>> trendingGifsResponseObservable;

    public GifRepository() {

    }

    public Observable<List<Gif>> getTrendingGifsResponseObservable(int limit, int offset) {

        apiInterface = NetworkService.getInstance().getNetworkClient();
        trendingGifsResponseObservable = apiInterface.getTrendingGifs(NetworkService.API_KEY,
                limit, offset)
                .map(result -> Observable.fromIterable(result.getListOfGifs()))
                .flatMap(x -> x)
                .toList().toObservable();
        return trendingGifsResponseObservable;
    }
}
