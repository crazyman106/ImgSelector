package com.example.cusexcel.view;

/**
 * Created by Administrator on 2018/8/26.
 */

public class LinePoint {
    // 线段的开始和结束坐标
    /**
     * line.moveTo(x1,y1)
     * line.lineTo(x2,y2)
     * <p>
     * 一个LinePoint对象代表一条线段
     */
    private float x1, y1, x2, y2;

    public float getX1() {
        return x1;
    }

    public void setX1(float x1) {
        this.x1 = x1;
    }

    public float getY1() {
        return y1;
    }

    public void setY1(float y1) {
        this.y1 = y1;
    }

    public float getX2() {
        return x2;
    }

    public void setX2(float x2) {
        this.x2 = x2;
    }

    public float getY2() {
        return y2;
    }

    public void setY2(float y2) {
        this.y2 = y2;
    }
}
