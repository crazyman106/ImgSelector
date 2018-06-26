package com.fz.rx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lijunjie on 2018/6/11 0011.
 * @description
 */

public class CusPaint extends View {
    private Paint paint;

    public CusPaint(Context context) {
        super(context);
        init(context);
    }

    public CusPaint(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    private void init(Context context) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(20);
        setBackgroundColor(Color.GREEN);
        paint.setTextSize(150);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(70, 50, 50, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(70, 220, 50, paint);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(70, 440, 50, paint);

    }
}
