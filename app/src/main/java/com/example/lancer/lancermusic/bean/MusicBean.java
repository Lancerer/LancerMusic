package com.example.lancer.lancermusic.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Lancer on 2018/5/29.
 * 序列化bean对象，使之能在activity和service中传递信息
 */

public class MusicBean implements Parcelable {
    private long id;
    private String title;
    private String artist;
    private String album;
    private long albumId;
    private long duration;
    private long size;
    private String url;

    public MusicBean() {

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public static Creator<MusicBean> getCREATOR() {
        return CREATOR;
    }

    public MusicBean(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        albumId = in.readLong();
        duration = in.readLong();
        size = in.readLong();
        url = in.readString();
    }

    public static final Creator<MusicBean> CREATOR = new Creator<MusicBean>() {
        @Override
        public MusicBean createFromParcel(Parcel in) {
            return new MusicBean(in);
        }

        @Override
        public MusicBean[] newArray(int size) {
            return new MusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeLong(albumId);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeString(url);
    }

    @Override
    public String toString() {
        return "MusicBean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", albumId=" + albumId +
                ", duration=" + duration +
                ", size=" + size +
                ", url='" + url + '\'' +
                '}';
    }
}
