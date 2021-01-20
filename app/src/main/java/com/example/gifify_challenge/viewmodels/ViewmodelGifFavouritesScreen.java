package com.example.gifify_challenge.viewmodels;
import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.repository.Repository;
import java.util.List;

/*
 * Favourite Screen Viewmodel
 */
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
