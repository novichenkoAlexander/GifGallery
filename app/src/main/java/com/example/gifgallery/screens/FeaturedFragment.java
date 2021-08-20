package com.example.gifgallery.screens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.databinding.FeaturedFragmentBinding;
import com.example.gifgallery.screens.adapters.GifsAdapter;
import com.example.gifgallery.viewmodels.FeaturedViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FeaturedFragment extends Fragment {

    private FeaturedViewModel viewModel;
    private FeaturedFragmentBinding viewBinding;

    private GifsAdapter gifsAdapter;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    private final int limit = 20;
    private int offset = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewBinding = FeaturedFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(FeaturedViewModel.class);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getTrendingGifs(limit, offset);

        Observable<List<Gif>> initialGifsObservable = viewModel.getTrendingGifsResponseObservable();

        DisposableObserver<List<Gif>> initialObserver = getInitialObserver();
        compositeDisposable.add(initialObserver);

        initialGifsObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(initialObserver);
    }

    private DisposableObserver<List<Gif>> getInitialObserver() {
        return new DisposableObserver<List<Gif>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifList) {
                Log.d("InitialObserver", "onNext");
                gifsAdapter = new GifsAdapter(gifList, requireContext());
                gifsAdapter.setLoadMoreListener(() -> {
                    offset += limit;
                    loadMoreGifs();
                });
                viewBinding.rvGifs.setAdapter(gifsAdapter);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("InitialObserver", "onComplete");
            }
        };
    }

    private void loadMoreGifs() {
        if (offset > 4999) {
            offset = 0;
        }
        DisposableObserver<List<Gif>> loadAfterObserver = getLoadAfterObserver();
        compositeDisposable.add(loadAfterObserver);

        viewModel.getTrendingGifs(limit, offset);
        Observable<List<Gif>> loadAfterObservable = viewModel.getTrendingGifsResponseObservable();
        loadAfterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadAfterObserver);
    }

    private DisposableObserver<List<Gif>> getLoadAfterObserver() {
        return new DisposableObserver<List<Gif>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifList) {
                Log.d("LoadAfterObserver", "onNExt");
                gifsAdapter.addGifs(gifList);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                Log.d("LoadAfterObserver", "onComplete");
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }


}