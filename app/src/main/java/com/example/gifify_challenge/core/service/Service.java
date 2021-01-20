package com.example.gifify_challenge.core.service;
import com.example.gifify_challenge.core.entities.DataContainer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*
 * Retrofit calls to Giphy API
 */
public interface Service {

    // Gif List
    @GET("trending")
    Call<DataContainer> getRandomGifList(
            @Query("api_key") String apiKey,
            @Query("limit") int limit,
            @Query("offset") int offset);

    // Queries for search
    @GET("search")
    Call<DataContainer> searchGifs(
            @Query("api_key") String apiKey,
            @Query("q") String query);
}
