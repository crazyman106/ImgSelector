package com.example.administrator.paint;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class DragView extends android.support.v7.widget.AppCompatImageView {

    private int width;
    private int height;
    private int screenWidth;
    private int screenHeight;
    private Context context;

    //是否拖动
    private boolean isDrag = false;

    public boolean isDrag() {
        return isDrag;
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    public int getStatusBarHeight() {
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        return getResources().getDimensionPixelSize(resourceId);
    }


    private float downX;
    private float downY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (this.isEnabled()) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    isDrag = false;
                    downX = event.getX();
                    downY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.e("kid", "ACTION_MOVE");
                    int l, r, t, b;
                    //当水平或者垂直滑动距离大于10,才算拖动事件

                    isDrag = true;
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    l = (int) (getLeft() + xDistance);
                    r = l + getWidth();
                    t = (int) (getTop() + yDistance);
                    b = t + getHeight();
                    this.layout(l, t, r, b);
                    break;
                case MotionEvent.ACTION_UP:
                    setPressed(false);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setPressed(false);
                    break;
            }
            return true;
        }
        return false;
    }

}