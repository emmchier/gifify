package com.example.gifify_challenge.core.repository;
import android.app.Application;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.RetrofitService;
import com.example.gifify_challenge.core.service.Service;
import com.example.gifify_challenge.core.service.ServiceResult;
import com.example.gifify_challenge.utils.Const;
import com.example.gifify_challenge.room.RoomDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private RoomDataSource room;
    private Application application;
    private final MutableLiveData<ServiceResult<DataContainer>> results;
    private final MutableLiveData<ServiceResult<DataContainer>> searchResults;

    public Repository(Application application) {
        this.application = application;
        this.results = new MutableLiveData<>();
        this.searchResults = new MutableLiveData<>();
    }

    // get data from Giphy API
    public MutableLiveData<ServiceResult<DataContainer>> getGifList(int paginationNumber) {

        results.postValue(new ServiceResult<>(ServiceResult.Status.LOADING, null, null));

        RetrofitService.getRetrofitInstance().create(Service.class)
                .getRandomGifList(Const.GIPHY_API_KEY, 25, paginationNumber)
                .enqueue(new Callback<DataContainer>() {
            @Override
            public void onResponse(Call<DataContainer> call, Response<DataContainer> response) {
                if (response.isSuccessful()) {
                    results.postValue(new ServiceResult<>(ServiceResult.Status.SUCCESS, response.body(), null));
                    Log.d("retrofit", response.body().toString());
                } else {
                    results.postValue(new ServiceResult<>(ServiceResult.Status.ERROR, null, "Ocurrió un error"));
                    Log.d("retrofit", response.message());
                }
            }
            @Override
            public void onFailure(Call<DataContainer> call, Throwable t) {
                results.postValue(new ServiceResult<>(ServiceResult.Status.ERROR, null, "Ocurrió un error"));
            }
        });
        return results;
    }

    public MutableLiveData<ServiceResult<DataContainer>> searchGifs(String query) {

        searchResults.postValue(new ServiceResult<>(ServiceResult.Status.LOADING, null, null));

        RetrofitService.getRetrofitInstance().create(Service.class)
            .searchGifs(Const.GIPHY_API_KEY, query)
            .enqueue(new Callback<DataContainer>() {
                @Override
                public void onResponse(Call<DataContainer> call, Response<DataContainer> response) {
                    if (response.isSuccessful()) {
                        searchResults.postValue(new ServiceResult<>(ServiceResult.Status.SUCCESS, response.body(), null));
                    } else {
                        searchResults.postValue(new ServiceResult<>(ServiceResult.Status.ERROR, null, "Ocurrió un error"));
                        Log.d("retrofit", response.message());
                    }
                }
                @Override
                public void onFailure(Call<DataContainer> call, Throwable t) {
                    searchResults.postValue(new ServiceResult<>(ServiceResult.Status.ERROR, null, "Ocurrió un error"));
                }
            });
            return searchResults;

    }

    public List<GifEntity> getGifFavouriteList() {
        room = new RoomDataSource(application.getApplicationContext());
        return room.getAllFavouriteGifList();
    }

    public void insertFavouriteGif(GifEntity favouriteGif) {
        room = new RoomDataSource(application.getApplicationContext());
        room.insertFavouriteGif(favouriteGif);
    }

    public void deleteFavouriteGif(String gifId) {
        room = new RoomDataSource(application.getApplicationContext());
        room.deleteFavouriteGif(gifId);
    }
}
