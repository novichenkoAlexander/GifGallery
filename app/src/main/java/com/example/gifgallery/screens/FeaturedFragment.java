package com.example.gifgallery.screens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.gifgallery.databinding.FeaturedFragmentBinding;
import com.example.gifgallery.viewmodels.FeaturedViewModel;


public class FeaturedFragment extends Fragment {

    private FeaturedViewModel viewModel;
    private FeaturedFragmentBinding viewBinding;
    private FeaturedGifsListAdapter adapter;


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

        viewModel.getTrendingGifs();

        adapter = new FeaturedGifsListAdapter() {
            @Override
            public void loadMore() {
                viewModel.getTrendingGifs();
            }
        };

        viewModel.gifsLiveData.observe(getViewLifecycleOwner(), adapter::submitList);
        viewBinding.rvGifs.setAdapter(adapter);

    }

}