package com.example.gifify_challenge.core.entities.sizes;

public class DownsizedMedium {
    private String url;
    private String width;
    private String height;
    private String size;

    public DownsizedMedium(String url, String width, String height, String size) {
        this.url = url;
        this.width = width;
        this.height = height;
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
