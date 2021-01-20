package com.example.gifify_challenge.core.entities;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import com.example.gifify_challenge.core.entities.sizes.Downsized;
import com.google.gson.annotations.SerializedName;

/*
 * Image format entity
 */
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
