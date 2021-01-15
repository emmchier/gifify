package com.example.gifify_challenge.core.repository;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.RetrofitService;
import com.example.gifify_challenge.core.service.Service;
import com.example.gifify_challenge.core.utils.Const;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    public MutableLiveData<DataContainer> getGifList() {
        
        final MutableLiveData<DataContainer> results = new MutableLiveData<>();

        RetrofitService.getRetrofitInstance().create(Service.class)
                .getRandomGifList(Const.GIPHY_API_KEY, 20)
                .enqueue(new Callback<DataContainer>() {
            @Override
            public void onResponse(Call<DataContainer> call, Response<DataContainer> response) {
                if (response.isSuccessful()) {
                    results.setValue(response.body());
                    Log.d("retrofit", response.body().toString());
                } else {
                    results.setValue(null);
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
}
