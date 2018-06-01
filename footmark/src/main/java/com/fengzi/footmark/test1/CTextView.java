package com.fengzi.footmark.test1;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * @author lijunjie on 2018/5/12 0012.
 * @description
 */

public class CTextView extends TextView {
    public CTextView(Context context) {
        super(context);
    }

    public CTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    float lastX, lastY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                //计算移动的距离
                int offsetX = (int) (x - lastX);
                int offsetY = (int) (y - lastY);
                //调用layout方法来重新放置它的位置
                layout(getLeft()+offsetX, getTop()+offsetY,
                        getRight()+offsetX , getBottom()+offsetY);
                return true;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }
}
