package com.example.gifify_challenge.ui.fragments.fragmentSearchGifsScreen;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.ServiceResult;
import com.example.gifify_challenge.databinding.FragmentSearchGifScreenBinding;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.adapters.AdapterSearchGifScreen;
import com.example.gifify_challenge.ui.dialogs.DialogBase;
import com.example.gifify_challenge.ui.fragments.fragmentGifFavouritesScreen.FragmentGifFavouritesScreen;
import com.example.gifify_challenge.ui.fragments.fragmentGifListScreen.FragmentGifListScreen;
import com.example.gifify_challenge.utils.SpacingItemDecoration;
import com.example.gifify_challenge.utils.Tools;
import com.example.gifify_challenge.utils.Util;
import com.example.gifify_challenge.viewmodels.ViewmodelSearchGifScreen;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.UUID;

public class FragmentSearchGifScreen extends Fragment implements AdapterSearchGifScreen.SearchListener {

    private AdapterSearchGifScreen adapterSearchGifScreen;
    private ViewmodelSearchGifScreen viewmodelSearchGifScreen;
    private FragmentSearchGifScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSearchGifScreenBinding.inflate(inflater, container, false);
        View fragmentSearchView = binding.getRoot();
        initViews();
        initRecyclerViewGifList();
        observeViewmodelData();
        setSearchView();

        return fragmentSearchView;
    }

    private void observeViewmodelData() {
        viewmodelSearchGifScreen = ViewModelProviders.of(requireActivity()).get(ViewmodelSearchGifScreen.class);

        // observe data from viewmodel
        final Observer<ServiceResult<DataContainer>> searchContainerObserver = new Observer<ServiceResult<DataContainer>>() {
            @Override
            public void onChanged(ServiceResult<DataContainer> dataContainer) {
                switch(dataContainer.getStatus()) {
                    case SUCCESS:
                        if (dataContainer.getData().getData() != null) {
                            binding.progressBarSearch.progressBarWait.setVisibility(View.GONE);
                            binding.progressBarSearch.overlay.setVisibility(View.GONE);
                            adapterSearchGifScreen.loadSearchGifs(dataContainer.getData().getData());

                            if (adapterSearchGifScreen.getItemCount() == 0) {
                                binding.imageViewDefaultSearch.linearDefaultSearchContainer.setVisibility(View.VISIBLE);
                            } else {
                                binding.imageViewDefaultSearch.linearDefaultSearchContainer.setVisibility(View.GONE);
                            }
                        }
                        break;
                    case ERROR:
                        binding.progressBarSearch.progressBarWait.setVisibility(View.VISIBLE);
                        binding.progressBarSearch.overlay.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), "An error has ocurred", Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        binding.progressBarSearch.progressBarWait.setVisibility(View.VISIBLE);
                        binding.progressBarSearch.overlay.setVisibility(View.VISIBLE);
                        binding.imageViewDefaultSearch.linearDefaultSearchContainer.setVisibility(View.GONE);
                        break;
                }
            }
        };
        viewmodelSearchGifScreen.searchList().observe(requireActivity(), searchContainerObserver);
    }

    private void initRecyclerViewGifList() {
        adapterSearchGifScreen = new AdapterSearchGifScreen(this);
        binding.recyclerViewSearchList.setAdapter(adapterSearchGifScreen);
        binding.recyclerViewSearchList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewSearchList.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        binding.recyclerViewSearchList.setHasFixedSize(true);
    }

    private void initViews() {
        binding.imargeViewBackFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(FragmentSearchGifScreen.this)
                        .navigate(R.id.action_to_list);
                Util.hideKeyboardFrom(getContext(), view);
            }
        });
    }

    @Override
    public void addToFavourite(GifEntity gif) {
        DialogBase dialogBase = new DialogBase(
                gif,
                "",
                "ADD TO FAVOURITES",
                "See More",
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewmodelSearchGifScreen.insertFavouriteGif(gif);
                        Util.setActionSnackBar(
                                getView(),
                                "Added to favourites",
                                "MORE",
                                Snackbar.LENGTH_LONG,
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        NavHostFragment
                                                .findNavController(FragmentSearchGifScreen.this)
                                                .navigate(R.id.action_to_favourites);
                                    }
                                });
                    }
                },
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        adapterSearchGifScreen.clearList();
                        NavHostFragment
                                .findNavController(FragmentSearchGifScreen.this)
                                .navigate(R.id.action_to_favourites);
                    }
                }
        );
        dialogBase.show(getChildFragmentManager(), "Dialog add to favourites");
    }

    @Override
    public void shareGif(GifEntity gif) {
        Util.shareGif(gif, getContext(), getChildFragmentManager());
    }

    private void setSearchView() {
        binding.searchViewGifs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                binding.recyclerViewSearchList.setVisibility(View.VISIBLE);
                viewmodelSearchGifScreen.getSearchGifs(newText);
                return true;
            }
        });
    }
}