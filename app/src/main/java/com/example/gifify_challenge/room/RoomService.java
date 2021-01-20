package com.example.gifify_challenge.room;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.ServiceResult;

import java.util.List;

@Dao
public interface RoomService {

    @Query("SELECT * FROM GifEntity")
    List<GifEntity> getAllFavouriteGifs();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGifFavourite(GifEntity favouriteGif);

    @Query("DELETE FROM GifEntity WHERE id = :id")
    void deleteFavouriteGif(String id);

}
