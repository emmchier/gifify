package com.example.gifify_challenge.core.entities;
import java.util.List;

/*
 * Container class for a Gif list
 */
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

}
