package com.example.administrator.colorfilter;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 *
 * @author Administrator
 * @date 2018/7/3
 */

public class BreakTextView extends View {
    public BreakTextView(Context context) {
        this(context, null);
    }

    Paint mPaint;

    public BreakTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 15, context.getResources().getDisplayMetrics()));
    }

    String txt = "abcdefghijklmn";
    private static final String STR = "看风景Beijing";

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float[] value = new float[5];
        int ret = mPaint.breakText(STR, true, 200, value);
        Rect rect = new Rect();
        mPaint.getTextBounds(STR, 0, STR.length(), rect);
        Log.e("YYYY", "breakText=" + ret + ", STR=" + STR.length() + ", value=" + value[0] + "-" + rect.right);
//breakText=5, STR=8, value=195.0
        canvas.drawText(STR, 0, Math.abs(mPaint.getFontMetrics().ascent), mPaint);
    }
}
