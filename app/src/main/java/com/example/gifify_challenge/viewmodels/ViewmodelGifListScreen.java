package com.example.gifify_challenge.viewmodels;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.repository.Repository;
import java.util.List;

public class ViewmodelGifListScreen extends ViewModel {
    private MutableLiveData<DataContainer> gifEntityList;
    private Repository repository;

    public ViewmodelGifListScreen() {
        gifEntityList = new MutableLiveData<>();
        repository = new Repository();
        getGiftList();
    }

    public LiveData<DataContainer> gifList() {
        return gifEntityList;
    }

    private void getGiftList() {
        gifEntityList = repository.getGifList();
    }
}
