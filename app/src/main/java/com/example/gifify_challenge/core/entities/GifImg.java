package com.example.gifify_challenge.core.entities;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.gifify_challenge.core.entities.sizes.Downsized;
import com.example.gifify_challenge.core.entities.sizes.DownsizedLarge;
import com.example.gifify_challenge.core.entities.sizes.DownsizedMedium;
import com.example.gifify_challenge.core.entities.sizes.DownsizedSmall;
import com.example.gifify_challenge.core.entities.sizes.DownsizedStill;
import com.example.gifify_challenge.core.entities.sizes.Looping;
import com.example.gifify_challenge.core.entities.sizes.PreviewGif;
import com.google.gson.annotations.SerializedName;

public class GifImg implements Parcelable {

    @SerializedName("downsized")
    private Downsized downsized;

    public GifImg(Downsized downsized) {
        this.downsized = downsized;
    }

    @NonNull
    public Downsized getDownsized() {
        return downsized;
    }

    public void setDownsized(Downsized downsized) {
        this.downsized = downsized;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.downsized, flags);
    }

    protected GifImg(Parcel in) {
        this.downsized = in.readParcelable(getClass().getClassLoader());
    }

    public static final Parcelable.Creator<GifImg> CREATOR = new Parcelable.Creator<GifImg>() {
        @Override
        public GifImg createFromParcel(Parcel source) {
            return new GifImg(source);
        }

        @Override
        public GifImg[] newArray(int size) {
            return new GifImg[size];
        }
    };
}
