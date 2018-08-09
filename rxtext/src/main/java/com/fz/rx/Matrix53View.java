package com.fz.rx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class Matrix53View extends View {

    Matrix mMatrix = new Matrix();

    public Matrix53View(Context context) {
        super(context);

    }

    public Matrix53View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mMatrix.setScale(2f, 2f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setColor(0xffc44187);

        //绘制 圆心
        canvas.drawPoint(100, 100, paint);
        //绘制 圆
        canvas.drawCircle(100, 100, 100, paint);

        //用黑色 绘制 放大 0.9倍
        paint.setColor(Color.BLACK);
        canvas.drawPoint(190, 190, paint);

        //使用矩阵放大一倍
        canvas.concat(mMatrix);
        //canvas.setMatrix(mMatrix);

        //按部就班
        paint.setColor(Color.RED);
        canvas.drawCircle(100, 100, 100, paint);
        canvas.drawPoint(100, 100, paint);
    }
}