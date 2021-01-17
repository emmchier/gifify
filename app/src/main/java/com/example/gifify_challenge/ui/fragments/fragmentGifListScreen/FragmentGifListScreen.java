package com.example.gifify_challenge.ui.fragments.fragmentGifListScreen;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.databinding.FragmentGifListScreenBinding;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.fragments.fragmentGifFavouritesScreen.FragmentGifFavouritesScreen;
import com.example.gifify_challenge.viewmodels.ViewmodelGifListScreen;

import java.util.List;

public class FragmentGifListScreen extends Fragment implements AdapterGifListScreen.GifListener,
        SwipeRefreshLayout.OnRefreshListener {

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
        viewmodelGifListScreen.getSearchGifs("cat");

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

        // observe search results from viewmodel
        final Observer<DataContainer> dataSearchObserver = new Observer<DataContainer>() {
            @Override
            public void onChanged(DataContainer dataContainer) {
                if (dataContainer != null) {
                    adapterGifListScreen.loadGifs(dataContainer.getData());
                }
            }
        };
        viewmodelGifListScreen.searchList().observe(requireActivity(), dataSearchObserver);
    }

    private void initRecyclerViewGifList() {
        adapterGifListScreen = new AdapterGifListScreen(this);
        binding.recyclerViewGifList.setAdapter(adapterGifListScreen);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewGifList.setLayoutManager(layoutManager);
    }

    private void initViews() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.swipeReflesh.setOnRefreshListener(this);
    }

    @Override
    public void addToFavourite(GifEntity gif) {
        viewmodelGifListScreen.insertFavouriteGif(gif);
        Toast.makeText(getContext(), "AGREGAR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void shareGif(GifEntity gif) {
        Toast.makeText(getContext(), "COMPARTIR", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        viewmodelGifListScreen.gifList();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.swipeReflesh.setRefreshing(false);
            }
        }, 2000);
    }

    private void setUpSearchView(String query) {

        viewmodelGifListScreen.getSearchGifs(query);

        binding.searchViewGifs.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }
}