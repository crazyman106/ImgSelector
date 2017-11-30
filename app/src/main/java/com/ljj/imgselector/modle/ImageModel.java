package com.ljj.imgselector.modle;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.ljj.imgselector.bean.Folder;
import com.ljj.imgselector.bean.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ImageModel {
    /**
     * 从SDCard加载图片
     *
     * @param context
     * @param callback
     */
    public static void loadImageForSDCard(final Context context, final DataCallback callback) {
        //由于扫描图片是耗时的操作，所以要在子线程处理。
        new Thread(new Runnable() {
            @Override
            public void run() {
                //扫描图片
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = context.getContentResolver();
                String[] projection = {
                        MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA,
                        MediaStore.Images.Media.DISPLAY_NAME,
                        MediaStore.Images.Media.DATE_ADDED
                };
//                Cursor mCursor = mContentResolver.query(mImageUri, new String[]{
//                                MediaStore.Images.Media.DATA,
//                                MediaStore.Images.Media.DISPLAY_NAME,
//                                MediaStore.Images.Media.DATE_ADDED,
//                                MediaStore.Images.Media._ID},
//                        null,
//                        null,
//                        MediaStore.Images.Media.DATE_ADDED+" desc");
                Cursor mCursor = MediaStore.Images.Media.query
                        (mContentResolver, mImageUri, projection,
                                null, null,
                                MediaStore.Images.Media.DATE_ADDED + " asc");
                ArrayList<Image> images = new ArrayList<>();

                //读取扫描到的图片
                if (mCursor != null) {
                    while (mCursor.moveToNext()) {
                        // 获取图片的路径
                        String path = mCursor.getString(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                        //获取图片名称
                        String name = mCursor.getString(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                        //获取图片时间
                        long time = mCursor.getLong(
                                mCursor.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                        //过滤未下载完成的文件
                        if (!".downloading".equals(path)) {
                            images.add(new Image(path, time, name));
                        }
                    }
                    mCursor.close();
                }
                Collections.reverse(images);
                callback.onSuccess(splitFolder(images));
            }
        }).start();
    }

    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     *
     * @param images
     * @return
     */
    private static ArrayList<Folder> splitFolder(ArrayList<Image> images) {
        ArrayList<Folder> folders = new ArrayList<>();
        folders.add(new Folder("全部图片", images));

        if (images != null && !images.isEmpty()) {
            int size = images.size();
            for (int i = 0; i < size; i++) {
                String path = images.get(i).getPath();
                String name = getFolderName(path);
                if (!TextUtils.isEmpty(name)) {
                    Folder folder = getFolder(name, folders);
                    folder.addImage(images.get(i));
                }
            }
        }
        return folders;
    }

    /**
     * Java文件操作 获取文件扩展名
     */
    public static String getExtensionName(String filename) {
        if (filename != null && filename.length() > 0) {
            int dot = filename.lastIndexOf('.');
            if (dot > -1 && dot < filename.length() - 1) {
                return filename.substring(dot + 1);
            }
        }
        return "";
    }


    private static String getFolderPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            int indexOf = path.lastIndexOf(File.separator);
            return path.substring(0, indexOf);
        }
        return "";
    }

    /**
     * 跟着图片路径，获取图片文件夹名称
     *
     * @param path
     * @return
     */
    private static String getFolderName(String path) {
        if (!TextUtils.isEmpty(path)) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";
    }

    public static void updateFolderImgs(List<Folder> folders, String imgPath) {
        Folder folder0 = folders.get(0);
        folder0.getImgs().add(0, new Image(imgPath, getTimeByPath(imgPath), getImgName(imgPath)));
        String name = getFolderName(imgPath);
        Iterator<Folder> iterator = folders.iterator();
        Folder folder;
        Image image;
        while (iterator.hasNext()) {
            folder = iterator.next();
            if (folder.getName().equals(name)) {
                image = new Image(imgPath, getTimeByPath(imgPath), getImgName(imgPath));
                folder.addImage(image);
                return;
            }
        }
        folder = new Folder(name);
        image = new Image(imgPath, getTimeByPath(imgPath), getImgName(imgPath));
        folder.addImage(image);
        folders.add(folder);
    }

    private static String getImgName(String imgPath) {
        int indexOf = imgPath.lastIndexOf("/");
        return imgPath.substring(indexOf + 1);
    }

    private static Long getTimeByPath(String path) {
        if (!TextUtils.isEmpty(path)) {
            int endPot = path.indexOf(".png");
            int startPot = path.lastIndexOf("/");
            String time = path.substring(startPot + 1, endPot);
            return Long.parseLong(time);
        }
        return 0L;
    }

    private static Folder getFolder(String name, List<Folder> folders) {
        if (!folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                Folder folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        Folder newFolder = new Folder(name);
        folders.add(newFolder);
        return newFolder;
    }

    public interface DataCallback {
        void onSuccess(ArrayList<Folder> folders);
    }
}
