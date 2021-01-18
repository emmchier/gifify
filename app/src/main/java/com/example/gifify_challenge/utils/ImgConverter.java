package com.example.gifify_challenge.utils;
import androidx.room.TypeConverter;
import com.example.gifify_challenge.core.entities.GifImg;
import com.google.gson.Gson;

public class ImgConverter {

    private static Gson gson;

    @TypeConverter
    public static String imgToString(GifImg img) {
        gson = new Gson();
        return gson.toJson(img);
    }

    @TypeConverter
    public static GifImg stringToImg(String string) {
        gson = new Gson();
        return gson.fromJson(string, GifImg.class);
    }
}
