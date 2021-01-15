package com.example.gifify_challenge.ui.fragments.fragmentGifListScreen;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.example.gifify_challenge.R;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.viewmodels.ViewmodelGifListScreen;

import java.util.List;

public class FragmentGifListScreen extends Fragment {

    private ViewmodelGifListScreen viewmodelGifListScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gif_list_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewmodelGifListScreen = ViewModelProviders.of(requireActivity()).get(ViewmodelGifListScreen.class);

        viewmodelGifListScreen.gifList().observe(requireActivity(), new Observer<DataContainer>() {
            @Override
            public void onChanged(DataContainer gifEntities) {
                if (gifEntities != null) {
                    System.out.println(gifEntities.getData().size());
                } else {
                    System.out.println("SI anduvo");
                }
            }
        });
    }
}