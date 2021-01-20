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

public class ViewmodelGifListScreen extends AndroidViewModel {

    private MutableLiveData<ServiceResult<DataContainer>> gifEntityList;
    private int initialPage = 0;
    private Repository repository;

    public ViewmodelGifListScreen(Application application) {
        super(application);
        gifEntityList = new MutableLiveData<>();
        repository = new Repository(application);
        getGiftList();
    }

    private void getGiftList() {
        gifEntityList = repository.getGifList(initialPage);
    }

    public void insertFavouriteGif(GifEntity favouriteGif) {
        repository.insertFavouriteGif(favouriteGif);
    }

    public LiveData<ServiceResult<DataContainer>> gifList() {
        return gifEntityList;
    }

    public void getNextPage() {
        initialPage++;
        getGiftList();
    }
}
