package com.ljj.imgselector.bean;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunjie on 2017/11/23 0023.
 * @description
 */

public class Folder {
    private String name;
    private List<Image> imgs;
    private String path;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Folder(String name) {
        this.name = name;
    }

    public Folder(String name, List<Image> images) {
        this.name = name;
        this.imgs = images;
    }

    public void addImage(Image image) {
        if (image != null && !TextUtils.isEmpty(image.getPath())) {
            if (imgs == null) {
                imgs = new ArrayList<>();
            }
            imgs.add(image);
        }
    }

    @Override
    public String toString() {
        return "Folder{" +
                "name='" + name + '\'' +
                ", imgs=" + imgs +
                '}';
    }

    public List<Image> getImgs() {
        return imgs;
    }

    public void setImgs(List<Image> imgs) {
        this.imgs = imgs;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
