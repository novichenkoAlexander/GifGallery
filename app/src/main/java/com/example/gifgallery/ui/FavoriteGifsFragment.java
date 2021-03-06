package com.example.gifgallery.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.databinding.FavoriteFragmentBinding;
import com.example.gifgallery.ui.adapters.FavoriteGifsAdapter;
import com.example.gifgallery.viewmodels.FavoriteViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavoriteGifsFragment extends Fragment {

    private FavoriteViewModel viewModel;
    private FavoriteFragmentBinding viewBinding;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private FavoriteGifsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewBinding = FavoriteFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FavoriteViewModel.class);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Single<List<GifImage>> favoriteGifs = viewModel.getGifImageSingle();
        DisposableSingleObserver<List<GifImage>> single = getSingle();
        compositeDisposable.add(single);

        favoriteGifs
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(single);
    }

    private DisposableSingleObserver<List<GifImage>> getSingle() {
        return new DisposableSingleObserver<List<GifImage>>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<GifImage> gifImages) {
                Log.d(getClass().getName(), "onSuccess");
                adapter = new FavoriteGifsAdapter(gifImages);
                viewBinding.rvGifs.setAdapter(adapter);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d(getClass().getName(), "OnError");
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
