package com.example.administrator.colorfilter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.administrator.colorfilter.R;

/**
 * Created by Administrator on 2018/7/2.
 */

public class CusView extends View {

    private Paint mPaint;// 画笔
    private Context mContext;// 上下文环境引用
    private Bitmap bitmap;// 位图

    private int x, y;// 位图绘制时左上角的起点坐标
    private boolean isClick;// 用来标识控件是否被点击过，默认没有被点击 false

    public CusView(Context context) {
        this(context, null);
    }

    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mContext = context;

        // 初始化画笔
        initPaint();

        // 初始化资源
        initRes(context);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isClick){
                    mPaint.setColorFilter(null);
                    isClick = false;
                }else {
                    mPaint.setColorFilter(new LightingColorFilter(0xFFFFFFFF,
                            0X00FF0000));
                    isClick = true;
                }
                invalidate();
            }
        });

    }

    private void initPaint() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    }

    private void initRes(Context context) {
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.qustion_bg);
        /*
         * 计算位图绘制时左上角的坐标使其位于屏幕中心 屏幕坐标x轴向左偏移位图一半的宽度 屏幕坐标y轴向上偏移位图一半的高度
		 */
        x = MeasureUtil.getScreenSize((Activity) mContext)[0] / 2 - bitmap.getWidth() / 2;
        y = MeasureUtil.getScreenSize((Activity) mContext)[1] / 2 - bitmap.getHeight() / 2;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制位图
        canvas.drawBitmap(bitmap, x, y, mPaint);
    }
}
