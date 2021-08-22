package com.example.gifgallery.viewmodels;


import androidx.lifecycle.ViewModel;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.repositories.GifRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;


public class FeaturedViewModel extends ViewModel {

    private final GifRepository repository;

    public FeaturedViewModel() {
        repository = new GifRepository();
    }

    public Observable<List<Gif>> getTrendingGifs(int limit, int offset) {
        return repository.getTrendingGifsObservable(limit, offset);
    }

    public void addGifImageToDatabase(GifImage gifImage) {
        repository.addGifImageToDatabase(gifImage);
    }

}
