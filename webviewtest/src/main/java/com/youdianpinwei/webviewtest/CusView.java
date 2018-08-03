package com.youdianpinwei.webviewtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author lijunjie on 2018/7/31 0031.
 * @description
 */

public class CusView extends View {
    Paint paint;

    public CusView(Context context) {
        super(context);
    }

    public CusView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Shader shader = new RadialGradient(200, 200, 200, new int[]{Color.WHITE, Color.YELLOW, Color.GREEN, Color.RED}, new float[]{0.0f, 0.4f, 0.6f, 0.8f}, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        canvas.drawRect(0, 0, 400, 400, paint);
    }
}
