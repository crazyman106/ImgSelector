package com.ljj.imgselector.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * @author lijunjie on 2017/11/23 0023.
 * @description 正方形图片
 */

public class SquareImageView extends AppCompatImageView {
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int speH_Size = MeasureSpec.getSize(heightMeasureSpec);
        int speH_Mode = MeasureSpec.getMode(heightMeasureSpec);
        int speW_Size = MeasureSpec.getSize(widthMeasureSpec);
        int speW_Mode = MeasureSpec.getMode(widthMeasureSpec);
        //-2147483648--1073741824--0
        //0--1073741824;0--266
        Log.e("onMeasuer", speH_Mode + "--" + speW_Mode + ";" + speH_Size + "--" + speW_Size + ";" + MeasureSpec.AT_MOST + "--" + MeasureSpec.EXACTLY + "--" + MeasureSpec.UNSPECIFIED);
//        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        if (speH_Mode == MeasureSpec.EXACTLY) {
            if (speW_Mode == MeasureSpec.EXACTLY) {
                if (speH_Size > speW_Size) {
                    super.onMeasure(widthMeasureSpec, widthMeasureSpec);
                } else {
                    super.onMeasure(heightMeasureSpec, heightMeasureSpec);
                }
            } else if (speW_Mode == MeasureSpec.UNSPECIFIED) {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            } else {
                super.onMeasure(heightMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        }
    }
}
