package com.ljj.imgselector.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lijunjie on 2017/11/23 0023.
 * @description
 */

public class Image implements Parcelable {

    private String path;
    private String name;
    private Long time;

    @Override
    public String toString() {
        return "Image{" +
                "path='" + path + '\'' +
                ", name='" + name + '\'' +
                ", time=" + time +
                '}';
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public Long getTime() {
        return time;
    }

    public Image(String path, long time, String name) {
        this.path = path;
        this.time = time;
        this.name = name;
    }

    protected Image(Parcel in) {
        path = in.readString();
        name = in.readString();
        if (in.readByte() == 0) {
            time = null;
        } else {
            time = in.readLong();
        }
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeString(name);
        if (time == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(time);
        }
    }
}
