package com.duongtung.musicbox.utils;

/**
 * Created by duong on 11/9/2016.
 */
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Music track model.
 */
public class VideoItem implements Parcelable {
    private String title;
    private long duration;
    private Uri albumArtUri;
    private Uri fileUri;

    public VideoItem title(String title) {
        this.title = title;
        return this;
    }


    public VideoItem duration(long duration) {
        this.duration = duration;
        return this;
    }

    public VideoItem albumArtUri(Uri albumArtUri) {
        this.albumArtUri = albumArtUri;
        return this;
    }

    public VideoItem fileUri(Uri fileUri) {
        this.fileUri = fileUri;
        return this;
    }

    public String title() {
        return title;
    }


    public long duration() {
        return duration;
    }

    public Uri albumArtUri() {
        return albumArtUri;
    }

    public Uri fileUri() {
        return fileUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoItem item = (VideoItem) o;

        if (duration != item.duration) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (albumArtUri != null ? !albumArtUri.equals(item.albumArtUri) : item.albumArtUri != null)
            return false;
        return fileUri != null ? fileUri.equals(item.fileUri) : item.fileUri == null;

    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (albumArtUri != null ? albumArtUri.hashCode() : 0);
        result = 31 * result + (fileUri != null ? fileUri.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VideoItem{" +
                "title='" + title + '\'' +
                ", duration=" + duration +
                ", albumArtUri=" + albumArtUri +
                ", fileUri=" + fileUri +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeLong(this.duration);
        dest.writeParcelable(this.albumArtUri, 0);
        dest.writeParcelable(this.fileUri, 0);
    }

    public VideoItem() {
    }

    protected VideoItem(Parcel in) {
        this.title = in.readString();
        this.duration = in.readLong();
        this.albumArtUri = in.readParcelable(Uri.class.getClassLoader());
        this.fileUri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<VideoItem> CREATOR = new Creator<VideoItem>() {
        public VideoItem createFromParcel(Parcel source) {
            return new VideoItem(source);
        }

        public VideoItem[] newArray(int size) {
            return new VideoItem[size];
        }
    };
}
