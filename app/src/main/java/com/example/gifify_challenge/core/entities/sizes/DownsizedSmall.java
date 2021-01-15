package com.example.gifify_challenge.core.entities.sizes;

public class DownsizedSmall {
    private String mp4;
    private String width;
    private String height;
    private String mp4_size;

    public DownsizedSmall(String mp4, String width, String height, String mp4_size) {
        this.mp4 = mp4;
        this.width = width;
        this.height = height;
        this.mp4_size = mp4_size;
    }

    public String getMp4() {
        return mp4;
    }

    public void setMp4(String mp4) {
        this.mp4 = mp4;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getMp4_size() {
        return mp4_size;
    }

    public void setMp4_size(String mp4_size) {
        this.mp4_size = mp4_size;
    }
}
