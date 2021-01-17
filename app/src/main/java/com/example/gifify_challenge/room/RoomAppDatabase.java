package com.example.gifify_challenge.room;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.gifify_challenge.core.entities.DataContainer;
import com.example.gifify_challenge.core.entities.GifEntity;

@Database(entities = {GifEntity.class}, version = 1)
public abstract class RoomAppDatabase extends RoomDatabase {

    public abstract RoomService daoRoomGifFavourites();

}
