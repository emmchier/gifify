package com.example.gifify_challenge.viewmodels;
import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.repository.Repository;
import java.util.List;

public class ViewmodelGifFavouritesScreen extends AndroidViewModel {

    private MutableLiveData<List<GifEntity>> favouriteList;
    private Repository repository;

    public ViewmodelGifFavouritesScreen(Application application) {
        super(application);
        favouriteList = new MutableLiveData<>();
        repository = new Repository(application);
    }

    public void getFavouritetList() {
        favouriteList.setValue(repository.getGifFavouriteList());
    }

    public void deleteFavouriteGif(String gifId) {
        repository.deleteFavouriteGif(gifId);
    }

    public LiveData<List<GifEntity>> getFavouriteGifList() {
        return favouriteList;
    }

}
