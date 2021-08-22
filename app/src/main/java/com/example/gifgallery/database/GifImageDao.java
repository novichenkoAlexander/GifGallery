package com.example.gifgallery.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gifgallery.api.dto.GifImage;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class GifImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addGifImageToDatabase(GifImage gifImage);

    @Query("DELETE FROM gifs WHERE url=:gifUrl")
    public abstract Completable deleteGifByUrl(String gifUrl);

    @Query("SELECT * FROM gifs ORDER BY id ASC")
    public abstract Single<List<GifImage>> getFavoriteGifs();

}
