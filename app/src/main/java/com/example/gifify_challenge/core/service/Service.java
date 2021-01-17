package com.example.gifify_challenge.core.service;
import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.utils.Const;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("trending")
    Call<DataContainer> getRandomGifList(
            @Query("api_key") String apiKey,
            @Query("limit") int limit);

    @GET("search")
    Call<DataContainer> searchGifs(
            @Query("api_key") String apiKey,
            @Query("q") String query);
}
