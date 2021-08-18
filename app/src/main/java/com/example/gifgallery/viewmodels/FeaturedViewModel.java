package com.example.gifgallery.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.repositories.NetworkRepository;

import java.util.ArrayList;
import java.util.List;


public class FeaturedViewModel extends ViewModel {

    private NetworkRepository repository;
    public MutableLiveData<List<Gif>> gifsLiveData = new MutableLiveData<>();

    private final List<Gif> sumList = new ArrayList<>();

    public FeaturedViewModel() {
        repository = new NetworkRepository() {
            @Override
            public void handleErrors(Throwable throwable) {

            }

            @Override
            public void handleResults(List<Gif> gifs) {
                sumList.addAll(gifs);
                gifsLiveData.postValue(sumList);
            }
        };
    }

    public void getTrendingGifs() {
        repository.getFeatured();
    }

}
