package com.example.administrator.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

public class LoadingProgressView extends View {
    private final Paint mSRCPaint;

    private Paint mPaint;
    private Paint mTextPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap;
    private int y;
    private int x;

    private PorterDuffXfermode mMode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    private Bitmap bgBitmap;
    private Path mPath;
    private boolean isLeft;
    //    private int y;
    private int mWidth;
    private int mHeight;
    private int mPercent = 50;

    public LoadingProgressView(Context context) {
        this(context, null);
    }

    public LoadingProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStrokeWidth(10);


        mPath = new Path();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.parseColor("#88f54548"));

        mSRCPaint = new Paint();
        mSRCPaint.setAntiAlias(true);
        mSRCPaint.setColor(Color.parseColor("#88dddddd"));

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);

        // 创建一个bitmap位图,高宽:500,500;颜色参数配置:8888ARGB四通道32位
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888);
        // 生成一个新的位图
        mCanvas = new Canvas(mBitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        }

        y = mHeight;
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (x > 50) {
            isLeft = true;
        } else if (x < 0) {
            isLeft = false;
        }
        if (isLeft) {
            x = x - 1;
        } else {
            x = x + 1;
        }
        mPath.reset();
        y = (int) ((1 - mPercent / 100f) * mHeight);
        mPath.moveTo(0, y);
        mPath.cubicTo(100 + x * 2, 50 + y, 100 + x * 2, y - 50, mWidth, y);
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        //清除掉图像 不然path会重叠
        // 给生产的位图填充颜色
        mBitmap.eraseColor(Color.parseColor("#00000000"));
        // 在画布的位图上画一个圆形
        mCanvas.drawCircle(mWidth / 2, mHeight / 2, mWidth / 2, mSRCPaint);
        // 设置混合模式
        mPaint.setXfermode(mMode);
        // 在画布上画贝塞尔曲线
        mCanvas.drawPath(mPath, mPaint);
        mPaint.setXfermode(null);

        // 将上述生成的bitmap画到屏幕上
        canvas.drawBitmap(mBitmap, 0, 0, null);
        String str = mPercent + "";
        mTextPaint.setTextSize(80);
        float txtLength = mTextPaint.measureText(str);
        canvas.drawText(str, mWidth / 2 - txtLength / 2, mHeight / 2 + 15, mTextPaint);
        mTextPaint.setTextSize(60);
        canvas.drawText("%", mWidth / 2 + 70, mHeight / 2 + 15, mTextPaint);
        postInvalidateDelayed(10);
    }

    public void setPercent(int percent) {
        mPercent = percent;
    }

}
