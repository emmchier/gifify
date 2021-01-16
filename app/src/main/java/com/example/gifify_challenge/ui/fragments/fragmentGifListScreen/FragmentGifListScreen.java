package com.example.gifify_challenge.ui.fragments.fragmentGifListScreen;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        binding.recyclerViewGifList.setLayoutManager(layoutManager);
    }

    private void initViews() {
        binding.progressBar.setVisibility(View.VISIBLE);
        binding.swipeReflesh.setOnRefreshListener(this);
    }

    @Override
    public void shareGif(Integer position) {
        Toast.makeText(getContext(), "Compartir en redes", Toast.LENGTH_SHORT).show();
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
}