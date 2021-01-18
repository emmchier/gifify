package com.example.gifify_challenge.ui.fragments.fragmentSearchGifsScreen;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.FragmentSearchGifScreenBinding;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.adapters.AdapterSearchGifScreen;
import com.example.gifify_challenge.viewmodels.ViewmodelSearchGifScreen;

import java.util.ArrayList;

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
        final Observer<DataContainer> searchContainerObserver = new Observer<DataContainer>() {
            @Override
            public void onChanged(DataContainer dataContainer) {
                if (dataContainer != null) {
                    adapterSearchGifScreen.loadSearchGifs(dataContainer.getData());
                }
            }
        };
        viewmodelSearchGifScreen.searchList().observe(requireActivity(), searchContainerObserver);

        // observe progressBar state from viewmodel
        final Observer<Integer> observerSearchDefaultImg = new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.imageViewSearchDefault.setVisibility(integer);
            }
        };
        viewmodelSearchGifScreen.searchDefaultImgShowing().observe(requireActivity(), observerSearchDefaultImg);

        // observe errors from service
        final Observer<Boolean> observerErrorFromService = new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean error) {
                if (error) {
                    Toast.makeText(getContext(), getString(R.string.error_system), Toast.LENGTH_SHORT).show();
                }
            }
        };
        viewmodelSearchGifScreen.isErrorService().observe(requireActivity(), observerErrorFromService);

    }

    private void initRecyclerViewGifList() {
        adapterSearchGifScreen = new AdapterSearchGifScreen(this);
        binding.recyclerViewSearchList.setAdapter(adapterSearchGifScreen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewSearchList.setLayoutManager(layoutManager);
    }

    private void initViews() {
        binding.recyclerViewSearchList.setVisibility(View.GONE);
        binding.imargeViewBackFromSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment
                        .findNavController(FragmentSearchGifScreen.this)
                        .navigate(R.id.action_to_list);
            }
        });
    }

    @Override
    public void addToFavourite(GifEntity gif) {
        viewmodelSearchGifScreen.insertFavouriteGif(gif);
        Toast.makeText(getContext(), "AGREGAR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareGif(GifEntity gif) {
        Toast.makeText(getContext(), "COMPARTIR", Toast.LENGTH_SHORT).show();
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