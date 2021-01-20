package com.example.gifify_challenge.ui.fragments.fragmentGifListScreen;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.ServiceResult;
import com.example.gifify_challenge.databinding.FragmentGifListScreenBinding;
import com.example.gifify_challenge.ui.adapters.AdapterGifListScreen;
import com.example.gifify_challenge.ui.dialogs.DialogBase;
import com.example.gifify_challenge.utils.SpacingItemDecoration;
import com.example.gifify_challenge.utils.Util;
import com.example.gifify_challenge.viewmodels.ViewmodelGifListScreen;
import com.google.android.material.snackbar.Snackbar;

/*
 * Gif List Screen
 */
public class FragmentGifListScreen extends Fragment implements AdapterGifListScreen.GifListener {

    private AdapterGifListScreen adapterGifListScreen;
    private ViewmodelGifListScreen viewmodelGifListScreen;
    private FragmentGifListScreenBinding binding;

    public FragmentGifListScreen() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TransitionInflater inflater = TransitionInflater.from(requireContext());
        setExitTransition(inflater.inflateTransition(R.transition.fade));
        setEnterTransition(inflater.inflateTransition(R.transition.fade));
    }

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

    // Observer Pattern
    private void observeViewmodelData() {
        viewmodelGifListScreen = ViewModelProviders.of(requireActivity()).get(ViewmodelGifListScreen.class);

        final Observer<ServiceResult<DataContainer>> dataContainerObserver = new Observer<ServiceResult<DataContainer>>() {
            @Override
            public void onChanged(ServiceResult<DataContainer> dataContainer) {
                switch(dataContainer.getStatus()) {
                    case SUCCESS:
                        if (dataContainer.getData().getData() != null) {
                            binding.progressBar.overlay.setVisibility(View.GONE);
                            binding.progressBar.progressBarWait.setVisibility(View.GONE);
                            adapterGifListScreen.loadGifs(dataContainer.getData().getData());
                            binding.imageViewDefaultList.linearDefaultListContainer.setVisibility(View.GONE);
                        }
                        break;
                    case ERROR:
                        binding.progressBar.overlay.setVisibility(View.VISIBLE);
                        binding.progressBar.progressBarWait.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), getString(R.string.unexpected_error), Toast.LENGTH_SHORT).show();
                        break;
                    case LOADING:
                        binding.progressBar.overlay.setVisibility(View.VISIBLE);
                        binding.progressBar.progressBarWait.setVisibility(View.VISIBLE);
                        binding.imageViewDefaultList.linearDefaultListContainer.setVisibility(View.GONE);
                        break;
                }
            }
        };
        viewmodelGifListScreen.gifList().observe(requireActivity(), dataContainerObserver);
    }

    private void initRecyclerViewGifList() {
        adapterGifListScreen = new AdapterGifListScreen(this);
        binding.recyclerViewGifList.setAdapter(adapterGifListScreen);
        binding.recyclerViewGifList.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewGifList.addItemDecoration(new SpacingItemDecoration(2, Util.dpToPx(getContext(), 3), true));
        binding.recyclerViewGifList.setHasFixedSize(true);
    }

    private void initViews() {
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
            getString(R.string.add_favourites),
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewmodelGifListScreen.insertFavouriteGif(gif);
                    Util.setActionSnackBar(
                        getView(),
                        getString(R.string.added_favourites),
                        getString(R.string.more_upper),
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
            }
        );
        dialogBase.show(getChildFragmentManager(), getString(R.string.dialog_favourites));
    }

    @Override
    public void shareGif(GifEntity gif) {
        Util.shareGif(gif, getContext(), getChildFragmentManager());
    }

    // Pagination method
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