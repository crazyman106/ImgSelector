package com.fengzi.footmark.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * @author lijunjie on 2018/5/31 0031.
 * @description
 */

public class CusListView extends ListView {
    private int flag = 0;
    private float StartX;
    private float StartY;

    public CusListView(Context context) {
        super(context);
    }

    public CusListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 总是调用listview的touch事件处理
        onTouchEvent(ev);
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            StartX = ev.getX();
            StartY = ev.getY();
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float ScollX = ev.getX() - StartX;
            float ScollY = ev.getY() - StartY;
            // 判断是横滑还是竖滑，竖滑的话拦截move事件和up事件（不拦截会由于listview和scrollview同时执行滑动卡顿）
            if (Math.abs(ScollX) < Math.abs(ScollY)) {
                flag = 1;
                return true;
            }
            return false;
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (flag == 1) {
                return true;
            }
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
