package com.example.gifgallery.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gifgallery.api.dto.GifImage;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class GifImageDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    public abstract void addGifImageToDatabase(GifImage gifImage);

    @Query("SELECT * FROM gifs ORDER BY url ASC")
    public abstract Single<List<GifImage>> getFavoriteGifs();
}
