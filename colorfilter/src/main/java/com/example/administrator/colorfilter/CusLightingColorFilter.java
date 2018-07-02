package com.example.administrator.colorfilter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.text.NumberFormat;

/**
 * Created by Administrator on 2018/7/1.
 */

public class CusLightingColorFilter extends View {
    public CusLightingColorFilter(Context context) {
        super(context);
    }

    public CusLightingColorFilter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CusLightingColorFilter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画笔
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画一张对比图
        setBackgroundColor(Color.GRAY);
        //图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.qustion_bg);
        //设置原图,用于对比
        canvas.drawBitmap(bitmap, 100, 100, paint);

        Bitmap myCopyBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        int width = myCopyBitmap.getWidth();
        int height = myCopyBitmap.getHeight();
        int[] oldPixels = new int[width * height];
        int[] newPixels = new int[width * height];
        int a, r, g, b, color;
        int R, G, B;
        bitmap.getPixels(oldPixels, 0, width, 0, 0, width, height);
        for (int i = 0; i < width * height; i++) {
            color = oldPixels[i];//取出每一个像素点
            r = Color.red(color);//取出当前像素点的R值
            g = Color.green(color);//取出当前像素点的G值
            b = Color.blue(color);////取出当前像素点的B值
            a = Color.alpha(color);////取出当前像素点的A值
            //然后通过相应的算法，重新修改这些值，并把最后的每个像素点保存到新的数组中
            //不同的效果对应着不同的算法:

            R = r * 0xff;
            G = 0x00;
            B = 0x00;
            if (R > 0 && R < 255) {
                Log.e("red", R + "");
            }
            newPixels[i] = Color.argb(a, R, G, B);
        }
        myCopyBitmap.setPixels(newPixels, 0, width, 0, 0, width, height);
        canvas.drawBitmap(myCopyBitmap, 500, 600, paint);


        //光照效果
        LightingColorFilter lightingColorFilter = new LightingColorFilter(0x00ff0000, 0x00000000);
        paint.setColorFilter(lightingColorFilter);
        canvas.drawBitmap(bitmap, 300, 1000, paint);


    }

}
