package com.example.gifify_challenge.utils;
import androidx.room.TypeConverter;

import com.example.gifify_challenge.core.entities.GifEntity;
import com.google.gson.Gson;

public class DataConverter {

    private static Gson gson;

    @TypeConverter
    public static String dataToString(GifEntity gif) {
        return gson.toJson(gif);
    }

    @TypeConverter
    public static GifEntity stringToGif(String string) {
        return gson.fromJson(string, GifEntity.class);
    }

}
