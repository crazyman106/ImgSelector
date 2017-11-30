package com.ljj.imgselector.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.ljj.imgselector.bean.Image;
import com.ljj.imgselector.photoview.PhotoView;
import com.ljj.imgselector.photoview.PhotoViewAttacher;
import com.ljj.imgselector.utils.ImageUtils;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lijunjie on 2017/11/24 0024.
 * @description
 */

public class ImageViewAdapter extends PagerAdapter {
    private List<Image> images;
    private List<PhotoView> photoViews;
    private Context mContext;


    public ImageViewAdapter(List<Image> images, Context mContext) {
        this.images = images;
        this.mContext = mContext;
        photoViews = new ArrayList<>(4);
        createImageViews();
    }

    private void createImageViews() {
        for (int i = 0; i < 4; i++) {
            PhotoView photoView = new PhotoView(mContext);
            photoView.setAdjustViewBounds(true);
            photoViews.add(photoView);
        }
    }

    @Override
    public int getCount() {
        return images == null ? 0 : images.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final PhotoView curPhotoView = photoViews.remove(0);
        Image image = images.get(position);
        container.addView(curPhotoView);
//        Glide.with(mContext).asBitmap().load(new File(image.getPath())).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
//                if (resource != null) {
//                    int width = resource.getWidth();
//                    int height = resource.getHeight();
//                    // 防止图片过大oom
//                    if (width > 8192 || height > 8192) {
//                        Bitmap bitmap = ImageUtils.zoomBitmap(resource, 8192, 8192);
//                        setBitmap2View(bitmap, curPhotoView);
//                    } else {
//                        setBitmap2View(resource, curPhotoView);
//                    }
//                }
//            }
//        });
        Glide.with(mContext).asBitmap().load(new File(image.getPath())).into(curPhotoView);
        curPhotoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick();
                }
            }
        });
        return curPhotoView;
    }

    private void setBitmap2View(Bitmap bitmap, PhotoView view) {
        view.setImageBitmap(bitmap);
        if (bitmap != null) {
            int bitmapWidth = bitmap.getWidth();
            int bitmapHeight = bitmap.getHeight();
            int viewWidth = view.getWidth();
            int viewHeight = view.getHeight();
            if (bitmapHeight != 0 && bitmapWidth != 0 && viewWidth != 0 && viewHeight != 0) {
                if (bitmapHeight / bitmapWidth > viewHeight / viewWidth) {
                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    float offset = (1.0f * bitmapHeight * viewWidth / bitmapWidth - viewHeight) / 2;
                    adjustOffset(view, offset);
                } else {
                    view.setScaleType(ImageView.ScaleType.FIT_CENTER);
                }
            }
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        if (object instanceof PhotoView) {
            PhotoView photoView = (PhotoView) object;
            photoView.setImageDrawable(null);
            photoViews.add(photoView);
            container.removeView(photoView);
        }
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    private void adjustOffset(PhotoView view, float offset) {
        PhotoViewAttacher attacher = view.getAttacher();
        try {
            Field field = PhotoViewAttacher.class.getDeclaredField("mBaseMatrix");
            field.setAccessible(true);
            Matrix matrix = (Matrix) field.get(attacher);
            matrix.postTranslate(0, offset);
            Method method = PhotoViewAttacher.class.getDeclaredMethod("resetMatrix");
            method.setAccessible(true);
            method.invoke(attacher);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setOnPhotoViewClickListener(OnPhotoViewClickListener listener) {
        this.listener = listener;
    }

    private OnPhotoViewClickListener listener;

    public interface OnPhotoViewClickListener {
        void onClick();
    }
}
