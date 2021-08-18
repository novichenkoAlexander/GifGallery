package com.example.gifgallery.network;

import com.example.gifgallery.api.GiphyApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {

    private static NetworkService mInstance;
    private static final String BASE_URL = "https://api.giphy.com/v1/gifs/";
    public static final String API_KEY = "Cep1sfi5cG7WwyjEQi9ifoJ8CalT9Qvd";
    public static final int LIMIT = 4;
    public static final int OFFSET = 0;
    private final Retrofit retrofit;

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    private NetworkService() {

        RxJava3CallAdapterFactory rxAdapter =
                RxJava3CallAdapterFactory
                        .createWithScheduler(Schedulers.io());

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request newRequest = chain.request().newBuilder()
                            .build();

                    return chain.proceed(newRequest);
                });

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(client.build())
                .build();
    }

    public GiphyApiInterface getNetworkClient() {
        return retrofit.create(GiphyApiInterface.class);
    }
}
