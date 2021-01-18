package com.example.gifify_challenge.core.entities;
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
