package com.example.administrator.porterduffxfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lijunjie on 2018/6/27 0027.
 * @description
 */

public class ColorFilterView extends View {

    private float[] mColorArray = new float[]{
            -1f, 0f, 0f, 0f, 255f,
            0f, -1f, 0f, 0f, 255f,
            0f, 0f, -1f, 0f, 255f,
            0f, 0f, 0f, 1f, 0f};
    Bitmap mBitMap;
    Paint mPaintFilter;
    ColorMatrix mColorMatrix;

    public ColorFilterView(Context context) {
        super(context);
        initData();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public ColorFilterView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData() {
        // 创建画笔
        mPaintFilter = new Paint(Paint.ANTI_ALIAS_FLAG);
        // 创建BitMap
        mBitMap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        // 新建颜色矩阵对象
        mColorMatrix = new ColorMatrix();
        // 设置颜色矩阵的值
        mColorMatrix.set(mColorArray);
//        mColorMatrix.setSaturation(1f);
//        mColorMatrix.setSaturation(0f);
//         mColorMatrix.setSaturation(2f);
//        ColorMatrix c1 = new ColorMatrix(new float[]{0.393f, 0.769f, 0.189f, 0, 0,
//                0.349f, 0.686f, 0.168f, 0, 0,
//                0.272f, 0.543f, 0.131f, 0, 0,
//                0, 0, 0, 1, 0});
//
//        ColorMatrix c2 = new ColorMatrix(new float[]{1.438f, -0.122f, -0.016f, 0, -0.03f,
//                -0.062f, 1.378f, -0.016f, 0, 0.05f,
//                -0.062f, -0.122f, 1.483f, 0, -0.02f,
//                0, 0, 0, 1, 0});
//        mColorMatrix.set(c1);
//        mColorMatrix.setConcat(c1, c2);
        int width = mBitMap.getWidth();
        int height = mBitMap.getHeight();
        Bitmap bmp = Bitmap.createBitmap(mBitMap.getWidth(), mBitMap.getHeight(), Bitmap.Config.ARGB_8888);
        int[] oldPixs = new int[width*height];
        int[] newPixs = new int[width*height];
        mBitMap.getPixels(oldPixs, 0, width, 0, 0, width, height);
        int color;
        int r,g,b;
        int r1,g1,b1,a;
        for (int i = 1; i < oldPixs.length; i++) {
            color = oldPixs[i];
            r = Color.red(color);
            g = Color.green(color);
            b = Color.blue(color);
            a = Color.alpha(color);

            r1 = 255 - r;
            g1 = 255 - g;
            b1 = 255 - b;
            newPixs[i] = Color.argb(a, r1, g1, b1);
        }
        bmp.setPixels(newPixs, 0, width, 0, 0, width, height);
        mBitMap = bmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        mColorMatrix.setRotate(0, degress);
        // 设置画笔颜色过滤器
        mPaintFilter.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        // 绘制处理后的图片
        canvas.drawBitmap(mBitMap, 0f, 0f, mPaintFilter);
    }

}
