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
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gifgallery.api.dto.GifImage;
import com.example.gifgallery.databinding.FavoriteFragmentBinding;
import com.example.gifgallery.ui.adapters.FavoriteGifsAdapter;
import com.example.gifgallery.ui.adapters.GifsAdapter;
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
    private GifsAdapter adapter;


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

        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                GifImage gifImage = adapter.getGifImageByPosition(viewHolder.getAdapterPosition());
                viewModel.deleteGifWithUndo(gifImage);
            }
        });
        touchHelper.attachToRecyclerView(viewBinding.rvGifs);
    }

    private DisposableSingleObserver<List<GifImage>> getSingle() {
        return new DisposableSingleObserver<List<GifImage>>() {
            @Override
            public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull List<GifImage> gifImages) {
                Log.d(getClass().getName(), "onSuccess");
                adapter = new GifsAdapter(gifImages);
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
