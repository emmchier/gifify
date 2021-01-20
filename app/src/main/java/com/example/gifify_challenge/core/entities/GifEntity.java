package com.example.gifify_challenge.core.entities;
import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import com.example.gifify_challenge.utils.ImgConverter;
import com.google.gson.annotations.SerializedName;

/*
 * Gif main entity
 */
@Entity
public class GifEntity implements Parcelable {

    @PrimaryKey
    @NonNull
    @SerializedName("id")
    private String id;
    @SerializedName("url")
    private String url;
    @SerializedName("title")
    private String title;
    @SerializedName("images")
    @TypeConverters(ImgConverter.class)
    private GifImg images;

    public GifEntity(String id, String url, String title, @NonNull GifImg images) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.images = images;
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

    public String getTitle() {
        return title;
    }

    @NonNull
    public GifImg getImages() {
        return images;
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
        dest.writeParcelable(this.images, flags);
    }

    protected GifEntity(Parcel in) {
        this.id = in.readString();
        this.url = in.readString();
        this.title = in.readString();
        this.images = in.readParcelable(getClass().getClassLoader());
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
