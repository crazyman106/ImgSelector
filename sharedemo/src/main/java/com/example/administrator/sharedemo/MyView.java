package com.example.administrator.sharedemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class MyView extends View {
    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);
        Log.i("chuan", "myView layout");
    }

    @Override
    protected void onAttachedToWindow() {
        Log.i("chuan", "myView onAttachedToWindow with" + getWidth());
        try {
            Object mAttachInfo = ReflectUtils.getField(View.class, "mAttachInfo");
            Log.i("chuan", "myView onAttachedToWindow mAttachInfo=null?" + (mAttachInfo == null));
            Object mRunQueue = ReflectUtils.getField(View.class, "mRunQueue");
            Log.i("chuan", "myView onAttachedToWindow mRunQueue=null?" + (mRunQueue == null));
        } catch (Exception e) {
        }
        super.onAttachedToWindow();
    }

    @Override
    public boolean post(Runnable action) {
        try {
            Object mAttachInfo = ReflectUtils.getField(View.class, "mAttachInfo");
            Log.i("chuan", "myView post mAttachInfo=null?" + (mAttachInfo == null));
        } catch (Exception e) {
        }
        return super.post(action);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("chuan", "myView onDraw");
    }
}