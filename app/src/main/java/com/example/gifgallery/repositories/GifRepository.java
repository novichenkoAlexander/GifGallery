package com.example.gifgallery.repositories;


import com.example.gifgallery.GIfGalleryApp;
import com.example.gifgallery.api.GiphyApiInterface;
import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.GiphyApiFactory;
import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.database.GifGalleryDatabase;
import com.example.gifgallery.database.GifImageDao;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class GifRepository {

    private final GiphyApiInterface apiInterface;
    private final GifImageDao gifImageDao;

    public GifRepository() {
        apiInterface = GiphyApiFactory.getInstance().getNetworkClient();
        gifImageDao = GIfGalleryApp.getDatabase().gifsDao();
    }

    public Observable<List<Gif>> getTrendingGifsObservable(int limit, int offset) {
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

    public void addGifImageToDatabase(GifImage gifImage) {
        GifGalleryDatabase.databaseWriteExecutor.execute(() -> gifImageDao.addGifImageToDatabase(gifImage));
    }

    public Single<List<GifImage>> getFavoritesGifsFromDatabase() {
        return gifImageDao.getFavoriteGifs();
    }

    public void updateGifImage(GifImage image) {
        GifGalleryDatabase.databaseWriteExecutor.execute(() -> gifImageDao.updateGif(image));
    }

}
