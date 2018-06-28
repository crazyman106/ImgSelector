package com.example.administrator.porterduffxfermode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
            1f, 120f, 0f, 120f, 0f,
            0f, 1f, 0f, 0f, 0f,
            0f, 0f, 1f, 0f, 0f,
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
        mBitMap = BitmapFactory.decodeResource(getResources(), R.mipmap.qustion_bg);
        // 新建颜色矩阵对象
        mColorMatrix = new ColorMatrix();
        // 设置颜色矩阵的值
        mColorMatrix.set(mColorArray);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 设置画笔颜色过滤器
        mPaintFilter.setColorFilter(new ColorMatrixColorFilter(mColorMatrix));
        // 绘制处理后的图片
        canvas.drawBitmap(mBitMap, 0f, 0f, mPaintFilter);
    }
}
