package com.example.gifify_challenge.ui.fragments.fragmentGifListScreen;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.service.autofill.OnClickAction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.FragmentGifListScreenBinding;
import com.example.gifify_challenge.ui.activities.MainActivity;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.dialogs.DialogBase;
import com.example.gifify_challenge.ui.fragments.fragmentGifFavouritesScreen.FragmentGifFavouritesScreen;
import com.example.gifify_challenge.ui.fragments.fragmentSearchGifsScreen.FragmentSearchGifScreen;
import com.example.gifify_challenge.utils.SpacingItemDecoration;
import com.example.gifify_challenge.utils.Tools;
import com.example.gifify_challenge.utils.Util;
import com.example.gifify_challenge.viewmodels.ViewmodelGifListScreen;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

public class FragmentGifListScreen extends Fragment implements AdapterGifListScreen.GifListener {

    private AdapterGifListScreen adapterGifListScreen;
    private ViewmodelGifListScreen viewmodelGifListScreen;
    private FragmentGifListScreenBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentGifListScreenBinding.inflate(inflater, container, false);
        View fragmentListView = binding.getRoot();
        initViews();
        initRecyclerViewGifList();
        observeViewmodelData();
        setPagination();

        return fragmentListView;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(FragmentGifListScreen.this)
                        .navigate(R.id.action_to_favourites);
            }
        });
    }

    private void observeViewmodelData() {
        viewmodelGifListScreen = ViewModelProviders.of(requireActivity()).get(ViewmodelGifListScreen.class);

        // observe data from viewmodel
        final Observer<DataContainer> dataContainerObserver = new Observer<DataContainer>() {
            @Override
            public void onChanged(DataContainer dataContainer) {
                if (dataContainer != null) {
                    adapterGifListScreen.loadGifs(dataContainer.getData());
                }
            }
        };
        viewmodelGifListScreen.gifList().observe(requireActivity(), dataContainerObserver);

        // observe progressBar state from viewmodel
        final Observer<Integer> observerProgressBar = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.progressBar.setVisibility(integer);
            }
        };
        viewmodelGifListScreen.progressBarShowing().observe(requireActivity(), observerProgressBar);

        // observe errors from service
        final Observer<Boolean> observerErrorFromService = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean error) {
                if (error) {
                    Toast.makeText(getContext(), getString(R.string.error_system), Toast.LENGTH_SHORT).show();
                }
            }
        };
        viewmodelGifListScreen.isErrorService().observe(requireActivity(), observerErrorFromService);

    }

    private void initRecyclerViewGifList() {
        adapterGifListScreen = new AdapterGifListScreen(this);
        binding.recyclerViewGifList.setAdapter(adapterGifListScreen);
        binding.recyclerViewGifList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewGifList.addItemDecoration(new SpacingItemDecoration(2, Tools.dpToPx(getContext(), 3), true));
        binding.recyclerViewGifList.setHasFixedSize(true);
    }

    private void initViews() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.linearToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(FragmentGifListScreen.this)
                        .navigate(R.id.action_to_search);
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
                    viewmodelGifListScreen.insertFavouriteGif(gif);
                    Util.setActionSnackBar(
                        getView(),
                        "Added to favourites",
                        "MORE",
                        Snackbar.LENGTH_LONG,
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                NavHostFragment
                                    .findNavController(FragmentGifListScreen.this)
                                    .navigate(R.id.action_to_favourites);
                            }
                        });
                }
            },
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    NavHostFragment
                        .findNavController(FragmentGifListScreen.this)
                        .navigate(R.id.action_to_favourites);
                }
            }
        );
        dialogBase.show(getChildFragmentManager(), "Dialog add to favourites");
    }

    @Override
    public void shareGif(GifEntity gif) {
        DialogBase dialogBase = new DialogBase(
            gif,
            "",
            "SHARE WITH FRIENDS!",
            "See More",
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Glide.with(getContext())
                        .asBitmap()
                        .load(gif.getImages().getDownsized().getUrl())
                        .into(new CustomTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                Uri uri = getImageUri(resource);
                                Intent shareIntent = new Intent();
                                shareIntent.setAction(Intent.ACTION_SEND);
                                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                                shareIntent.setType("image/gif");
                                startActivity(Intent.createChooser(shareIntent, "Share this GIF"));
                            }
                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                            }
                        });
                }
            },
            null
        );
        dialogBase.show(getChildFragmentManager(), "Share with friends");
    }

    public Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(),
                inImage, UUID.randomUUID().toString() + ".png", "drawing");
        return Uri.parse(path);
    }

    private void setPagination() {
        binding.recyclerViewGifList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!binding.recyclerViewGifList.canScrollVertically(RecyclerView.FOCUS_DOWN)) {
                    viewmodelGifListScreen.getNextPage();
                }
            }
        });
    }
}