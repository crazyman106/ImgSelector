package com.youdianpinwei.webviewtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lijunjie on 2018/8/1 0001.
 * @description
 */

public class CircleDownloadingView extends View {

    private int width, height;
    private Paint paintCircle, paintBitmap;
    private Bitmap srcBitmap;
    private int circleColor = Color.RED;
    private RectF circleRectF;

    public CircleDownloadingView(Context context) {
        this(context, null);
    }

    public CircleDownloadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleDownloadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setColor(circleColor);
        paintCircle.setStrokeWidth(10);
        paintCircle.setStyle(Paint.Style.STROKE);

        paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(width / 2, height / 2);
        canvas.rotate(-90);
        canvas.drawArc(circleRectF,
                0,
                360,
                false, paintCircle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (!(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY)) {
            throw new IllegalArgumentException("the width and height must be > 0");
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        circleRectF = new RectF(-width / 2 + 8, -height / 2 + 8, width / 2 - 8, height / 2 - 8);
    }


}
