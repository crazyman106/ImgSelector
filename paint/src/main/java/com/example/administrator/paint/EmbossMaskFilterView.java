package com.example.administrator.paint;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

public class EmbossMaskFilterView extends View {
    private static final int H_COUNT = 2, V_COUNT = 4;// 水平和垂直切割数
    private Paint mPaint;// 画笔
    private PointF[] mPointFs;// 存储各个巧克力坐上坐标的点

    private int width, height;// 单个巧克力宽高
    private float coorY;// 单个巧克力坐上Y轴坐标值

    public EmbossMaskFilterView(Context context) {
        this(context, null);
    }

    public EmbossMaskFilterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 不使用硬件加速
        setLayerType(LAYER_TYPE_SOFTWARE, null);

        // 初始化画笔
        initPaint();

        // 计算参数
        cal(context);
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        // 实例化画笔
        mPaint = new Paint();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xFF603811);

        // 设置画笔遮罩滤镜
//		mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.1F, 10F, 20F));
    }

    /**
     * 计算参数
     */
    private void cal(Context context) {
        int[] screenSize = MeasureUtil.getScreenSize((Activity) context);

        width = screenSize[0] / H_COUNT;
        height = screenSize[1] / V_COUNT;

        int count = V_COUNT * H_COUNT;

        mPointFs = new PointF[count];
        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                coorY = i * height / 2F;
                mPointFs[i] = new PointF(0, coorY);
            } else {
                mPointFs[i] = new PointF(width, coorY);
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
/*

      canvas.drawRect(10,0,100,100,mPaint);

        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1f, 1F }, 0.1F, 10F, 20F));
        canvas.drawRect(10,150,100,300,mPaint);

        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.5F, 10F, 20F));
        canvas.drawRect(10,300,100,450,mPaint);

        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 1F, 10F, 20F));
        canvas.drawRect(10,450,100,600,mPaint);


        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.5F, 0F, 20F));
        canvas.drawRect(10,600,100,750,mPaint);

        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.5F, 10F, 20F));
        canvas.drawRect(10,750,100,900,mPaint);

        mPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1F }, 0.5F, 50F, 20F));
        canvas.drawRect(10,900,100,1050,mPaint);
*/

        // 包含特殊符号的绘制（如 emoji 表情）
        String text = "Hello HenCoder \uD83C\uDDE8\uD83C\uDDF3"; // "Hello HenCoder 🇨🇳"

        int length = text.length();
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(20);
        mPaint.setTextSize(50);
        mPaint.setStyle(Paint.Style.FILL);
        float advance1 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length);
        canvas.drawText(text, 10, 300, mPaint);
        float advance2 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 1);
        float advance3 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 2);
        float advance4 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 3);
        float advance5 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 4);
        float advance6 = mPaint.getRunAdvance(text, 0, length, 0, length, false, length - 5);

        // 画矩形
//		for (int i = 0; i < V_COUNT * H_COUNT; i++) {
//			canvas.drawRect(mPointFs[i].x, mPointFs[i].y, mPointFs[i].x + width, mPointFs[i].y + height, mPaint);
//		}
    }
}