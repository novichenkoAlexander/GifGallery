package com.example.gifgallery.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.repositories.GifRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;

public class SearchViewModel extends ViewModel {

    private final GifRepository repository;
    private Observable<List<Gif>> searchGifsObservable;

    public SearchViewModel() {
        repository = new GifRepository();
    }

    public void makeSearch(String query, int limit, int offset) {
        searchGifsObservable = repository.getSearchObservable(query, limit, offset);
    }

    public Observable<List<Gif>> getSearchGifsObservable() {
        return searchGifsObservable;
    }
}
