package com.example.gifify_challenge.core.entities;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

public class GifEntity implements Parcelable {

    private String id;
    private String url;
    private String title;

    public GifEntity(String id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.url);
        dest.writeString(this.title);
    }

    protected GifEntity(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.title = in.readString();
    }

    public static final Parcelable.Creator<GifEntity> CREATOR = new Parcelable.Creator<GifEntity>() {
        @Override
        public GifEntity createFromParcel(Parcel source) {
            return new GifEntity(source);
        }

        @Override
        public GifEntity[] newArray(int size) {
            return new GifEntity[size];
        }
    };
}
