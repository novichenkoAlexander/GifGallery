package com.example.gifgallery.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gifgallery.api.dto.Gif;
import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.databinding.SearchFragmentBinding;
import com.example.gifgallery.ui.adapters.GifsAdapter;
import com.example.gifgallery.viewmodels.SearchViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchGifsFragment extends Fragment {

    private SearchFragmentBinding viewBinding;
    private SearchViewModel viewModel;
    private GifsAdapter gifsAdapter;

    private final int limit = 4;
    private int offset = 0;
    private String searchQuery;

    private final String TAG = getClass().getName();

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewBinding = SearchFragmentBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(SearchViewModel.class);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewBinding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchQuery = query;
                searchGifs();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        viewBinding.imgBtnSearch.setOnClickListener(v -> {
            searchQuery = viewBinding.search.getQuery().toString();
            if (!searchQuery.isEmpty()) {
                searchGifs();
            }
        });

    }

    private void searchGifs() {
        viewModel.makeSearch(searchQuery, limit, offset);

        Observable<List<Gif>> initialSearchObservable = viewModel.getSearchGifsObservable();
        DisposableObserver<List<Gif>> initialSearchObserver = getInitialObserver();
        compositeDisposable.add(initialSearchObserver);
        initialSearchObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(initialSearchObserver);
    }

    private DisposableObserver<List<Gif>> getInitialObserver() {
        return new DisposableObserver<List<Gif>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifList) {
                Log.d(TAG + " InitialObserver", "onNext");
                gifsAdapter = new GifsAdapter(gifList);
                gifsAdapter.setOnLongItemClickListener((v, position) -> {
                    GifImage gifImage = gifsAdapter.getGifImageByPosition(position);
                    viewModel.saveGifImageToDatabase(gifImage);
                });
                gifsAdapter.setLoadMoreListener(() -> {
                    offset += limit;
                    loadMoreGifs();
                });
                viewBinding.rvGifs.setAdapter(gifsAdapter);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d(TAG + " Initial Observer", "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG + " InitialObserver", "onComplete");
            }
        };
    }

    private void loadMoreGifs() {
        if (offset > 4999) {
            offset = 0;
        }
        DisposableObserver<List<Gif>> loadAfterObserver = getLoadAfterObserver();
        compositeDisposable.add(loadAfterObserver);

        viewModel.makeSearch(searchQuery, limit, offset);
        Observable<List<Gif>> loadAfterObservable = viewModel.getSearchGifsObservable();
        loadAfterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loadAfterObserver);
    }

    private DisposableObserver<List<Gif>> getLoadAfterObserver() {
        return new DisposableObserver<List<Gif>>() {
            @Override
            public void onNext(@io.reactivex.rxjava3.annotations.NonNull List<Gif> gifList) {
                Log.d(TAG + " LoadAfterObserver", "onNExt");
                gifsAdapter.addGifs(gifList);
            }

            @Override
            public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                Log.d(TAG + " LoadAfterObserver", "onError");
            }

            @Override
            public void onComplete() {
                Log.d(TAG + " LoadAfterObserver", "onComplete");
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
