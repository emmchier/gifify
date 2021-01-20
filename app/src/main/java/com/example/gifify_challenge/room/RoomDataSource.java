package com.example.gifify_challenge.room;
import android.content.Context;
import androidx.room.Room;

import com.example.gifify_challenge.core.entities.GifEntity;
import com.example.gifify_challenge.core.service.ServiceResult;

import java.util.List;

public class RoomDataSource {

    private RoomAppDatabase roomAppDatabase;

    public RoomDataSource(Context context) {
        roomAppDatabase = Room.databaseBuilder(
                context, RoomAppDatabase.class, "database-name"
        ).allowMainThreadQueries().build();
    }

    // get from room the complete favourite gif list
    public List<GifEntity> getAllFavouriteGifList() {
        List<GifEntity> gifList = roomAppDatabase.daoRoomGifFavourites().getAllFavouriteGifs();
        roomAppDatabase.close();
        return gifList;
    }

    // add one favourite gif to room favourite gif list
    public void insertFavouriteGif(GifEntity favoriteGif) {
        roomAppDatabase.daoRoomGifFavourites().insertGifFavourite(favoriteGif);
        roomAppDatabase.close();
    }

    // delete one gif from the room favourite gif list
    public void deleteFavouriteGif(String gifId) {
        roomAppDatabase.daoRoomGifFavourites().deleteFavouriteGif(gifId);
        roomAppDatabase.close();
    }

}
