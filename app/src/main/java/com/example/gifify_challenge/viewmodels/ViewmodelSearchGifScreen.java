package com.example.gifify_challenge.viewmodels;
import android.app.Application;
import android.view.View;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.repository.Repository;
import com.example.gifify_challenge.core.service.ServiceResult;

import java.util.List;

public class ViewmodelSearchGifScreen extends AndroidViewModel {

    private MutableLiveData<ServiceResult<DataContainer>> searchGifList;
    private Repository repository;

    public ViewmodelSearchGifScreen(Application application) {
        super(application);
        searchGifList = new MutableLiveData<>();
        repository = new Repository(application);
        getSearchGifs("");
    }

    public void getSearchGifs(String query) {
        searchGifList = repository.searchGifs(query);
    }

    public void insertFavouriteGif(GifEntity favouriteGif) {
        repository.insertFavouriteGif(favouriteGif);
    }

    public LiveData<ServiceResult<DataContainer>> searchList() {
        return searchGifList;
    }

}
