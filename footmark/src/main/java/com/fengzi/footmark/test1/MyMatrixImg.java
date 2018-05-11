package com.fengzi.footmark.test1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fengzi.footmark.R;

public class MyMatrixImg extends android.support.v7.widget.AppCompatImageView {

    private Context mContext;

    private float startX,startY;


    public MyMatrixImg(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

        // 初始化
        init();
    }

    private void init() {


        /*
         * 获取屏幕宽高
         */

        WindowManager manager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int Screenwidth = outMetrics.widthPixels;
        int Screenheight = outMetrics.heightPixels;

        /*
         * 设置图片资源
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        bitmap = Bitmap.createScaledBitmap(bitmap, Screenwidth, Screenheight, true);
        setImageBitmap(bitmap);
    }
}