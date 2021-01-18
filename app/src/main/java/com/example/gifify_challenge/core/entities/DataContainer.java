package com.example.gifify_challenge.core.entities;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.gifify_challenge.core.utils.DataConverter;
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class DataContainer {

    private List<GifEntity> data;

    public DataContainer() {
    }

    public DataContainer(List<GifEntity> data) {
        this.data = data;
    }

    public List<GifEntity> getData() {
        return data;
    }

    public void setData(List<GifEntity> data) {
        this.data = data;
    }
}
