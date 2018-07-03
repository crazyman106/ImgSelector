package com.example.administrator.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author lijunjie on 2018/7/2 0002.
 * @description
 */

public class FontMetricxView extends View {
    Paint mPaint;
    String text = "ac";
    Paint.FontMetrics mFontMetrics;

    public FontMetricxView(Context context) {
        this(context, null);
    }

    public FontMetricxView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextSize(80);
        mPaint.setColor(Color.RED);
        mFontMetrics = mPaint.getFontMetrics();
        Log.e("FontMetricxView", "ascent：" + mFontMetrics.ascent);
        Log.e("FontMetricxView", "top：" + mFontMetrics.top);
        Log.e("FontMetricxView", "leading：" + mFontMetrics.leading);
        Log.e("FontMetricxView", "descent：" + mFontMetrics.descent);
        Log.e("FontMetricxView", "bottom：" + mFontMetrics.bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, 0, Math.abs(mFontMetrics.bottom), mPaint);
//        canvas.drawText(text, 0, (Math.abs(mFontMetrics.bottom) + Math.abs(mFontMetrics.top)) * 2, mPaint);
        Log.e("FontMetricxView", "width：" + getWidth());
        Log.e("FontMetricxView", "height：" + getHeight());
    }
}
