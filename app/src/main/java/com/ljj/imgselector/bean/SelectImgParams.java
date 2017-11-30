package com.ljj.imgselector.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author lijunjie on 2017/11/28 0028.
 * @description
 */

public class SelectImgParams implements Parcelable {

    private int galleryMode;

    private int camearMode;

    private int cropMode;

    private String imageFolderPath;

    public String getImageFolderPath() {
        return imageFolderPath;
    }

    public void setImageFolderPath(String imageFolderPath) {
        this.imageFolderPath = imageFolderPath;
    }

    public int getCamearMode() {
        return camearMode;
    }

    public int getCropMode() {
        return cropMode;
    }

    public void setCamearMode(int camearMode) {
        this.camearMode = camearMode;
    }

    public void setCropMode(int cropMode) {
        this.cropMode = cropMode;
    }

    public int getGalleryMode() {
        return galleryMode;
    }

    public void setGalleryMode(int galleryMode) {
        this.galleryMode = galleryMode;
    }

    protected SelectImgParams(Parcel in) {
        galleryMode = in.readInt();
    }

    public SelectImgParams(int mode) {
        this.galleryMode = mode;
        this.camearMode = -1;
        this.cropMode = -1;
    }

    public SelectImgParams() {

    }

    public SelectImgParams(int galleryMode, int camearMode, int cropMode) {
        this.galleryMode = galleryMode;
        this.camearMode = camearMode;
        this.cropMode = cropMode;
    }


    public static final Creator<SelectImgParams> CREATOR = new Creator<SelectImgParams>() {
        @Override
        public SelectImgParams createFromParcel(Parcel in) {
            return new SelectImgParams(in);
        }

        @Override
        public SelectImgParams[] newArray(int size) {
            return new SelectImgParams[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(galleryMode);
        dest.writeInt(camearMode);
        dest.writeInt(cropMode);
        dest.writeString(imageFolderPath);
    }

}
