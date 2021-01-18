package com.example.gifify_challenge.viewmodels;
import android.app.Application;
import android.view.View;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.repository.Repository;
import java.util.List;

public class ViewmodelSearchGifScreen extends AndroidViewModel {

    private MutableLiveData<DataContainer> searchGifList;
    private MutableLiveData<Boolean> errorService;
    private MutableLiveData<Integer> progressBar;
    private MutableLiveData<Integer> searchDefaultImg;

    private Repository repository;

    public ViewmodelSearchGifScreen(Application application) {
        super(application);
        searchGifList = new MutableLiveData<>();
        errorService = new MutableLiveData<>();
        progressBar = new MutableLiveData<>();
        searchDefaultImg = new MutableLiveData<>();
        repository = new Repository(application);
        getSearchGifs("");
    }

    public void getSearchGifs(String query) {
        searchDefaultImg.setValue(View.VISIBLE);
        searchGifList.postValue(repository.searchGifs(query).getValue());
    }

    public void insertFavouriteGif(GifEntity favouriteGif) {
        repository.insertFavouriteGif(favouriteGif);
    }

    public LiveData<DataContainer> searchList() {
        return searchGifList;
    }

    public LiveData<Integer> progressBarShowing() {
        return progressBar;
    }

    public LiveData<Integer> searchDefaultImgShowing() {
        return searchDefaultImg;
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
