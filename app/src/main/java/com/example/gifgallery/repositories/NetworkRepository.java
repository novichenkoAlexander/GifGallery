package com.example.gifgallery.repositories;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.network.NetworkService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public abstract class NetworkRepository {

    private int offset;

    public void getFeatured() {

        offset += NetworkService.LIMIT;

        Observable<List<Gif>> observableResponse = NetworkService.getInstance()
                .getNetworkClient()
                .getTrendingGifs(NetworkService.API_KEY, NetworkService.LIMIT, offset)
                .map(result -> Observable.fromIterable(result.getListOfGifs()))
                .flatMap(x -> x)
                .toList().toObservable();

        observableResponse
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleErrors);
    }

    public abstract void handleErrors(Throwable throwable);

    public abstract void handleResults(List<Gif> gifs);


}
