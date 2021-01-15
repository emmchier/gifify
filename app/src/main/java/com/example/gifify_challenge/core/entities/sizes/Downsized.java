package com.example.gifify_challenge.core.entities.sizes;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class Downsized implements Parcelable {
    @SerializedName("url")
    private String url;
    @SerializedName("width")
    private String width;
    @SerializedName("height")
    private String height;
    @SerializedName("size")
    private String size;

    public Downsized(String url, String width, String height, String size) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.width);
        dest.writeString(this.height);
        dest.writeString(this.size);
    }

    protected Downsized(Parcel in) {
        this.url = in.readString();
        this.width = in.readString();
        this.height = in.readString();
        this.size = in.readParcelable(getClass().getClassLoader());
    }

    public static final Parcelable.Creator<Downsized> CREATOR = new Parcelable.Creator<Downsized>() {
        @Override
        public Downsized createFromParcel(Parcel source) {
            return new Downsized(source);
        }

        @Override
        public Downsized[] newArray(int size) {
            return new Downsized[size];
        }
    };
}
