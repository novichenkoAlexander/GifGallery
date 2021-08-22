package com.example.gifgallery.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GiphyApiFactory {

    private static GiphyApiFactory mInstance;
    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/";
    public static final String API_KEY = "Cep1sfi5cG7WwyjEQi9ifoJ8CalT9Qvd";
    private final Retrofit retrofit;

    public static GiphyApiFactory getInstance() {
        if (mInstance == null) {
            mInstance = new GiphyApiFactory();
        }
        return mInstance;
    }

    private GiphyApiFactory() {

        RxJava3CallAdapterFactory rxAdapter =
                RxJava3CallAdapterFactory
                        .createWithScheduler(Schedulers.io());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build();
    }

    public GiphyApiInterface getNetworkClient() {
        return retrofit.create(GiphyApiInterface.class);
    }
}
