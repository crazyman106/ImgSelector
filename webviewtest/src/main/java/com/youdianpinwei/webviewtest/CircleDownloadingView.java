package com.youdianpinwei.webviewtest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author lijunjie on 2018/8/1 0001.
 * @description
 */

public class CircleDownloadingView extends View {

    private int width, height;
    private Paint paintCircle, paintBitmap, paintBg;
    private Bitmap srcBitmap;
    private int circleColor = Color.RED, circleBgColor = Color.WHITE;
    private RectF circleRectF;
    private float circleWidth;
    private int type = NONE;
    private float degress = 50;

    public static final int NONE = 0;
    public static final int DOWNLOADING = 1;
    public static final int PAUSE = 2;
    public static final int DOWNLOADED = 3;

    public CircleDownloadingView(Context context) {
        this(context, null);
    }

    public CircleDownloadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleDownloadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleDownloadingView);
        circleColor = typedArray.getColor(R.styleable.CircleDownloadingView_BorderColor, Color.RED);
        circleWidth = typedArray.getDimension(R.styleable.CircleDownloadingView_BorderWidth, 5f);
        type = typedArray.getInt(R.styleable.CircleDownloadingView_LoadingType, NONE);
        circleBgColor = typedArray.getColor(R.styleable.CircleDownloadingView_BorderBgColor, Color.WHITE);
        typedArray.recycle();

        paintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintCircle.setColor(circleColor);
        paintCircle.setStrokeWidth(circleWidth);
        paintCircle.setStyle(Paint.Style.STROKE);
        paintBitmap = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintBg = new Paint();
        paintBg.set(paintCircle);
        paintBg.setColor(circleBgColor);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (type == DOWNLOADED) {
            setClickable(false);
        }
        float sweepAngle = 360f * (degress / 100f);
        Log.e("sweepangle", sweepAngle + "");
        canvas.save();
        canvas.translate(width / 2, height / 2);
        canvas.rotate(-90);
        canvas.drawArc(circleRectF,
                0,
                360,
                false, paintBg);
        switch (type) {
            case NONE:
                srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_download_no);
                break;
            case DOWNLOADING:
                srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_downloading);
                canvas.drawArc(circleRectF,
                        0,
                        sweepAngle,
                        false, paintCircle);

                break;
            case PAUSE:
                srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_download_pause);
                canvas.drawArc(circleRectF,
                        0,
                        sweepAngle,
                        false, paintCircle);
                break;
            case DOWNLOADED:
                srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.circle_download_ok);
                canvas.drawArc(circleRectF,
                        0,
                        360,
                        false, paintCircle);
                break;
        }
        canvas.restore();
        int imgWidth = srcBitmap.getWidth();
        int imgHeight = srcBitmap.getHeight();
        if (imgWidth > width || imgHeight > height) {
            throw new IllegalArgumentException("the width or height of srcBitmap more than this view");
        }
        int left = width / 2 - imgWidth / 2;
        int top = height / 2 - imgHeight / 2;
        canvas.drawBitmap(srcBitmap, left, top, paintBitmap);
    }

    public void setProgress(int progress) {
        this.degress = progress;
        invalidate();
    }

    public void setType(int type) {
        this.type = type;
        invalidate();
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
