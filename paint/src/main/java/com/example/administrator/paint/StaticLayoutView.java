package com.example.administrator.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

public class StaticLayoutView extends View {
    private static final String TEXT = "This is used by widgets to control text layout. You should not need to use this class directly unless you are implementing your own widget or custom display object, or would be tempted to call Canvas.drawText() directly.";
    private TextPaint mTextPaint;// 文本的画笔
    private StaticLayout mStaticLayout;// 文本布局

    public StaticLayoutView(Context context) {
        this(context, null);
    }

    public StaticLayoutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 初始化画笔
        initPaint();
    }

    int i = 0;

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
       /* mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(50);
        mTextPaint.setColor(Color.BLACK);*/
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        canvas.drawText(i + "", 500, 200, mPaint);

        mPaint.setFontFeatureSettings("tnum");
        canvas.drawText(i + "", 500, 280, mPaint);
      /*  canvas.save();
        mStaticLayout = new StaticLayout(TEXT, mTextPaint, canvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, false);
        mStaticLayout.draw(canvas);
        canvas.restore();*/
        if (i == 0) {
            mHandler.sendEmptyMessageDelayed(0, 50);
        }
    }


    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            i++;
            mHandler.sendEmptyMessageDelayed(0, 50);
            postInvalidate();
        }
    };
}