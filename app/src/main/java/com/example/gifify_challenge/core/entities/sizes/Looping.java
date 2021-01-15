package com.example.gifify_challenge.core.entities.sizes;

import com.google.gson.annotations.SerializedName;

public class Looping {
    @SerializedName("mp4")
    private String mp4;

    public Looping(String mp4) {
        this.mp4 = mp4;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }
}
