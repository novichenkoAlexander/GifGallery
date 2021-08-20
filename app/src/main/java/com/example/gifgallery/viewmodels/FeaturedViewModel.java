package com.example.gifgallery.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.repositories.GifRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class FeaturedViewModel extends ViewModel {

    private Observable<List<Gif>> trendingGifsResponseObservable;
    private final GifRepository repository;

    public FeaturedViewModel() {
        repository = new GifRepository();
    }

    public void getTrendingGifs(int limit, int offset) {
        trendingGifsResponseObservable = repository.getTrendingGifsResponseObservable(limit, offset);
    }

    public Observable<List<Gif>> getTrendingGifsResponseObservable() {
        return trendingGifsResponseObservable;
    }

}
