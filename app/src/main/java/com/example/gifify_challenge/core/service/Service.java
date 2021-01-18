package com.example.gifify_challenge.core.service;
import com.example.gifify_challenge.core.entities.DataContainer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("trending")
    Call<DataContainer> getRandomGifList(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset);

    @GET("search")
    Call<DataContainer> searchGifs(
            @Query("api_key") String apiKey,
            @Query("q") String query);
}
