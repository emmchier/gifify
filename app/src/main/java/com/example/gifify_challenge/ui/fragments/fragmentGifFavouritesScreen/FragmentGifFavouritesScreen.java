package com.example.gifify_challenge.ui.fragments.fragmentGifFavouritesScreen;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.FragmentGifFavouritesScreenBinding;
import com.example.gifify_challenge.databinding.FragmentGifListScreenBinding;
import com.example.gifify_challenge.ui.adapters.AdapterGifFavouritesScreen;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.fragments.fragmentGifListScreen.FragmentGifListScreen;
import com.example.gifify_challenge.viewmodels.ViewmodelGifFavouritesScreen;
import com.example.gifify_challenge.viewmodels.ViewmodelGifListScreen;

import java.util.ArrayList;
import java.util.List;

public class FragmentGifFavouritesScreen extends Fragment implements AdapterGifFavouritesScreen.FavouriteListener {

    private AdapterGifFavouritesScreen adapterGifFavouritesScreen;
    private ViewmodelGifFavouritesScreen viewmodelGifFavouritesScreen;
    private FragmentGifFavouritesScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGifFavouritesScreenBinding.inflate(inflater, container, false);
        View fragmentFavouriteView = binding.getRoot();
        initRecyclerViewFavouriteList();

        return fragmentFavouriteView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeViewmodelData();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewmodelGifFavouritesScreen.getFavouritetList();
    }

    private void observeViewmodelData() {
        viewmodelGifFavouritesScreen = ViewModelProviders.of(requireActivity()).get(ViewmodelGifFavouritesScreen.class);

        // observe data from viewmodel
        final Observer<List<GifEntity>> observerFavouriteList = new Observer<List<GifEntity>>() {
            @Override
            public void onChanged(List<GifEntity> gifEntities) {
                if (gifEntities != null) {
                    adapterGifFavouritesScreen.loadFavourites(gifEntities);
                } else {
                    adapterGifFavouritesScreen.loadFavourites(new ArrayList<>());
                }
            }
        };
        viewmodelGifFavouritesScreen.getFavouriteGifList().observe(requireActivity(), observerFavouriteList);
    }

    private void initRecyclerViewFavouriteList() {
        adapterGifFavouritesScreen = new AdapterGifFavouritesScreen(this);
        binding.recyclerViewGifFavourite.setAdapter(adapterGifFavouritesScreen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewGifFavourite.setLayoutManager(layoutManager);
    }

    @Override
    public void shareGif(GifEntity gif) {
        Toast.makeText(getContext(), "COMPARTIR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deleteFromList(GifEntity gif) {
        viewmodelGifFavouritesScreen.deleteFavouriteGif(gif.getId());
        Toast.makeText(getContext(), "ELIMINADO", Toast.LENGTH_SHORT).show();
    }
}