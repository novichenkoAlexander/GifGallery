package com.example.gifgallery.screens;

import android.os.Bundle;
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
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class FeaturedFragment extends Fragment {

    private FeaturedViewModel viewModel;
    private FeaturedFragmentBinding viewBinding;

    private GifsAdapter gifsAdapter;

    private final int limit = 10;
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

        viewModel.getTrendingGifs(10, 0);

        viewModel.getTrendingGifsResponseObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Gif>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifs) {
                        gifsAdapter = new GifsAdapter(gifs);
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

                    }
                });
    }

    private void loadMoreGifs() {
        if (offset > 4999) {
            offset = 0;
        }
        viewModel.getTrendingGifs(limit, offset);
        viewModel.getTrendingGifsResponseObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Gif>>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifList) {
                        gifsAdapter.addGifs(gifList);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}