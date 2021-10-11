package com.example.gifgallery.viewmodels;

import androidx.lifecycle.ViewModel;

import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.repositories.GifRepository;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class FavoriteViewModel extends ViewModel {

    private final GifRepository repository;

    public FavoriteViewModel() {
        this.repository = new GifRepository();
    }

    public Single<List<GifImage>> getGifImageSingle() {
        return repository.getFavoritesGifsFromDatabase();
    }

    public void deleteGifWithUndo(GifImage gifImage) {
        gifImage.setDeleted(true);
        repository.updateGifImage(gifImage);
    }
}
