package com.example.gifgallery.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.gifgallery.api.dto.GifImage;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

@Dao
public abstract class GifImageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addGifImageToDatabase(GifImage gifImage);

    @Query("SELECT * FROM gifs ORDER BY url ASC")
    public abstract Single<List<GifImage>> getFavoriteGifs();

    @Delete
    public abstract void deleteGif(GifImage gifImage);

    @Update
    public abstract void updateGif(GifImage gifImage);
}
