package com.ljj.imgselector.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * @author lijunjie on 2017/11/24 0024.
 * @description
 */

public class ImageUtils {


    public static Bitmap zoomBitmap(Bitmap bm, int width, int height) {
        int oldWidth = bm.getWidth();
        int oldHeight = bm.getHeight();
        // 计算缩放比例
        float scoleWidth = width / oldHeight;
        float scoleHeight = height / oldHeight;
        float scole = Math.min(scoleHeight, scoleWidth);
        Matrix matrix = new Matrix();
        matrix.postScale(scole, scole);
        Bitmap bitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    public static int[] getBitmapOffset(ImageView img, Boolean includeLayout) {
        int[] offset = new int[2];
        float[] values = new float[9];
        Matrix m = img.getImageMatrix();
        m.getValues(values);
        offset[0] = (int) values[5];
        offset[1] = (int) values[2];
        if (includeLayout) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) img.getLayoutParams();
            int paddingTop = img.getPaddingTop();
            int paddingLeft = img.getPaddingLeft();
            offset[0] += paddingTop + lp.topMargin;
            offset[1] += paddingLeft + lp.leftMargin;
        }
        return offset;
    }
}
