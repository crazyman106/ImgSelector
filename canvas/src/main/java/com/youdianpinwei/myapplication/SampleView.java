package com.youdianpinwei.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

public class SampleView extends View {
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 定义顶点的坐标数组
    private float[] mVerts = new float[10];
    // 定义纹理数组
    private float[] mTexs = new float[10];
    private float[] mTexs2 = new float[10];

    // 定义顶点的绘制顺序数组
    private short[] mIndices = new short[]{0, 1, 2, 3, 4, 1};

    private Matrix mMatrix = new Matrix();
    private Matrix mInverse = new Matrix();
    private Bitmap bm;


    private void setXY(float[] array, int index, float x, float y) {
        array[index * 2] = x;
        array[index * 2 + 1] = y;
    }

    public SampleView(Context context, AttributeSet attr) {
        this(context, attr, 0);
    }

    public SampleView(Context context, AttributeSet attr, int def) {
        super(context, attr, def);
        setFocusable(true);
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.refer_madness);

        Shader s = new BitmapShader(bm, Shader.TileMode.CLAMP,
                Shader.TileMode.CLAMP);
        mPaint.setShader(s);

        float w = bm.getWidth();
        float h = bm.getHeight();
        setXY(mTexs, 0, w / 2, h / 2);
        setXY(mTexs, 1, 0, 0);
        setXY(mTexs, 2, w, 0);
        setXY(mTexs, 3, w, h);
        setXY(mTexs, 4, 0, h);

        setXY(mTexs2, 0, w / 3, h / 3);
        setXY(mTexs2, 1, w, 0);
        setXY(mTexs2, 2, 0, 0);
        setXY(mTexs2, 3, 0, h);
        setXY(mTexs2, 4, w, h);

        setXY(mVerts, 0, w / 2, h / 2);
        setXY(mVerts, 1, 0, 0);
        setXY(mVerts, 2, w, 0);
        setXY(mVerts, 3, w, h);
        setXY(mVerts, 4, 0, h);

        // 矩阵缩放
        mMatrix.setScale(0.6f, 0.6f);
        // 平移,参数为横向和纵向的平移距离
        mMatrix.preTranslate(20, 20);
        // 矩阵反转
        mMatrix.invert(mInverse);
    }

    public SampleView(Context context) {
        this(context, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(0xffcccccc);
        canvas.save();
        canvas.concat(mMatrix);
        // canvas.drawBitmap(bm, 0, 0, mPaint);
            /*
             * 绘制顶点：第一个参数表示顶点的模式。TRIANGLE_FAN表示以一个点为三角形公共顶点，组成一系列相邻的三角形。
             * 第二个参数表示顶点坐标数组的长度，一个坐标由x和y两个值组成，所以该参数一定为偶数
             * 第三个参数表示顶点坐标数组；第四个参数顶点坐标数组的偏移量； 第五个参数表示纹理坐标数组；第六个参数为纹理坐标数组的偏移量
             * 第七个参数颜色数组，直接用颜色渲染顶点；第八个参数颜色数组的偏移量 第九个参数顶点绘制的索引；第十个参数顶点索引的偏移量
             * 第十一个参数表示绘制的索引数量，第十二个参数表示画笔
             */
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs, 0, null, 0, null, 0, 0, mPaint);
        canvas.translate(200, 0);
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs2, 0, null, 0, null, 0, 0, mPaint);
        canvas.translate(-200, 240);
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs, 0, null, 0, mIndices, 0, 6, mPaint);
        canvas.translate(200, 0);
        canvas.drawVertices(Canvas.VertexMode.TRIANGLE_FAN, 10, mVerts, 0,
                mTexs2, 0, null, 0, mIndices, 0, 6, mPaint);
        canvas.restore();

        /*canvas.save();
        canvas.drawBitmap(bm, 10, 200, mPaint);
        canvas.restore();*/
    }

}