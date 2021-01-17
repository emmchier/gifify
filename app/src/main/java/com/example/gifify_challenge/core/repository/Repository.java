package com.example.gifify_challenge.core.repository;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.RetrofitService;
import com.example.gifify_challenge.core.service.Service;
import com.example.gifify_challenge.core.utils.Const;
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

    public Repository(Application application) {
        this.application = application;
        this.favourites = new MutableLiveData<>();
    }

    // get data from Giphy API
    public MutableLiveData<DataContainer> getGifList() {
        
        final MutableLiveData<DataContainer> results = new MutableLiveData<>();

        RetrofitService.getRetrofitInstance().create(Service.class)
                .getRandomGifList(Const.GIPHY_API_KEY, 20)
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

    public List<GifEntity> getGifFavouriteList() {
        room = new RoomDataSource(application.getApplicationContext());
        //favourites.setValue(room.getAllFavouriteGifList());
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
