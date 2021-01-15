package com.example.gifify_challenge.core.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataContainer {
    @SerializedName("data")
    private List<GifEntity> data;

    public DataContainer() {
    }

    public List<GifEntity> getData() {
        return data;
    }

    public void setData(List<GifEntity> data) {
        this.data = data;
    }
}
