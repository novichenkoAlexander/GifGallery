package com.example.gifgallery.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.gifgallery.BuildConfig;
import com.example.gifgallery.api.dto.GifImage;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(
        entities = {GifImage.class},
        version = 1
)
public abstract class GifGalleryDatabase extends RoomDatabase {

    private static final String databaseName = BuildConfig.APPLICATION_ID + ".db";

    public abstract GifImageDao gifsDao();

    private static volatile GifGalleryDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static GifGalleryDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (GifGalleryDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            GifGalleryDatabase.class, databaseName)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
