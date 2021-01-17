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

public class ViewmodelGifListScreen extends AndroidViewModel {

    private MutableLiveData<DataContainer> gifEntityList;
    private MutableLiveData<Boolean> errorService;
    private MutableLiveData<Integer> progressBar;

    private Repository repository;

    public ViewmodelGifListScreen(Application application) {
        super(application);
        gifEntityList = new MutableLiveData<>();
        errorService = new MutableLiveData<>();
        progressBar = new MutableLiveData<>();
        repository = new Repository(application);
        getGiftList();
    }

    private void getGiftList() {
        progressBar.setValue(View.VISIBLE);
        gifEntityList = repository.getGifList();
    }

    public void insertFavouriteGif(GifEntity favouriteGif) {
        repository.insertFavouriteGif(favouriteGif);
    }

    public LiveData<DataContainer> gifList() {
        return gifEntityList;
    }

    public LiveData<Integer> progressBarShowing() {
        return progressBar;
    }

    public LiveData<Boolean> isErrorService() {
        return errorService;
    }

    public void responseList(List<DataContainer> list) {
        this.errorService.postValue(false);
        this.progressBar.postValue(View.GONE);
    }

    public void responseErrorService() {
        this.errorService.setValue(true);
        this.progressBar.postValue(View.GONE);
    }
}
