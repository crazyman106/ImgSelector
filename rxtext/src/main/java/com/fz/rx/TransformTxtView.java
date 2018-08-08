package com.fz.rx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.DynamicLayout;
import android.text.Layout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import java.util.logging.Handler;

/**
 * Created by Administrator on 2018/7/9.
 */

public class TransformTxtView extends View {
    private Matrix mMatrix;
    private Bitmap mBitmap;
    private int mWidth, mHeight;
    private Canvas mCanvas;
    private Paint mBmpPaint;
    private TextPaint mTxtPaint;
    private DynamicLayout mDynamicLayout;
    private String mTxtContent = "我  \n 是 \n  中\n 国 \n人  ";

    private float [] points = new float[9];


    public TransformTxtView(Context context) {
        this(context, null);
    }

    public TransformTxtView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformTxtView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mMatrix = new Matrix();


        // 文本属性
        mTxtPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTxtPaint.setTextSize(40);
        mTxtPaint.setColor(Color.RED);

        mBmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    public void setRotate(float degress) {
        mMatrix.postRotate(degress, mWidth / 2, mHeight / 2);
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDynamicLayout.draw(mCanvas);
        canvas.drawBitmap(mBitmap, 0, 0, mTxtPaint);
        canvas.concat(mMatrix);
        mMatrix.getValues(points);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w;
        this.mHeight = h;
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mDynamicLayout = new DynamicLayout(mTxtContent, mTxtPaint, mWidth, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
    }
}
