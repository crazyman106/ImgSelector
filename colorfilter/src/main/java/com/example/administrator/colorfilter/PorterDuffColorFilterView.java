package com.example.administrator.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2018/7/2.
 */

public class PorterDuffColorFilterView extends View {

    Paint mPaint;
    PorterDuffColorFilter mPDCFilter;
    Bitmap mBitmap;

    public PorterDuffColorFilterView(Context context) {
        this(context, null);
    }

    public PorterDuffColorFilterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPDCFilter = new PorterDuffColorFilter(Color.GREEN, PorterDuff.Mode.ADD);
        mPaint.setColorFilter(mPDCFilter);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.qustion_bg);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        canvas.drawBitmap(mBitmap, 0, mBitmap.getHeight() + 100, mPaint);
    }
}
