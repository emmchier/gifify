package com.example.gifify_challenge.core.repository;
import android.app.Application;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.RetrofitService;
import com.example.gifify_challenge.core.service.Service;
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
    private MutableLiveData<List<GifEntity>> favourites;

    private final MutableLiveData<DataContainer> results;
    private final MutableLiveData<DataContainer> searchResults;

    public Repository(Application application) {
        this.application = application;
        this.favourites = new MutableLiveData<>();
        this.results = new MutableLiveData<>();
        this.searchResults = new MutableLiveData<>();
    }

    // get data from Giphy API
    public MutableLiveData<DataContainer> getGifList(int paginationNumber) {

        results.postValue(new DataContainer(new ArrayList<>()));

        RetrofitService.getRetrofitInstance().create(Service.class)
                .getRandomGifList(Const.GIPHY_API_KEY, 25, paginationNumber)
                .enqueue(new Callback<DataContainer>() {
            @Override
            public void onResponse(Call<DataContainer> call, Response<DataContainer> response) {
                if (response.isSuccessful()) {
                    results.postValue(response.body());
                    Log.d("retrofit", response.body().toString());
                } else {
                    results.postValue(null);
                    Log.d("retrofit", response.message());
                }
            }
            @Override
            public void onFailure(Call<DataContainer> call, Throwable t) {
                Log.d("retrofit", call.request().url().toString());
            }
        });
        return results;
    }

    public MutableLiveData<DataContainer> searchGifs(String query) {

        searchResults.postValue(new DataContainer(new ArrayList<>()));

        RetrofitService.getRetrofitInstance().create(Service.class)
            .searchGifs(Const.GIPHY_API_KEY, query)
            .enqueue(new Callback<DataContainer>() {
                @Override
                public void onResponse(Call<DataContainer> call, Response<DataContainer> response) {
                    if (response.isSuccessful()) {
                        searchResults.postValue(response.body());
                    } else {
                        searchResults.postValue(null);
                        Log.d("retrofit", response.message());
                    }
                }
                @Override
                public void onFailure(Call<DataContainer> call, Throwable t) {
                    Log.d("retrofit", call.request().url().toString());
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
