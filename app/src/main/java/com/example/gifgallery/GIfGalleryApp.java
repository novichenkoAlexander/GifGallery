package com.example.gifgallery;

import android.app.Application;

import com.example.gifgallery.database.GifGalleryDatabase;

public class GIfGalleryApp extends Application {

    private static GifGalleryDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = GifGalleryDatabase.getInstance(this.getApplicationContext());
    }

    public static GifGalleryDatabase getDatabase() {
        return database;
    }
}
