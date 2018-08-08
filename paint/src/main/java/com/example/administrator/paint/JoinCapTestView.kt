package com.example.administrator.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author lijunjie on 2018/7/5 0005.
 * @description
 */

class JoinCapTestView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    lateinit var mPaint: Paint

    init {
        initParams()
    }

    fun initParams() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.RED;
        mPaint.strokeWidth = 80f;
    }

    override fun onDraw(canvas: Canvas?) {
//        // 线帽
//        mPaint.strokeCap = Paint.Cap.BUTT      // 画的线条两端没有圆角和者矩形，butt，无
//        canvas?.drawLine(100f, 100f, 400f, 100f, mPaint);
//
//        mPaint.strokeCap = Paint.Cap.ROUND     // 即画的线条两端带有圆角，ROUND，圆角
//        canvas?.drawLine(100f, 300f, 400f, 300f, mPaint);
//
//
//        mPaint.strokeCap = Paint.Cap.SQUARE     // 画的线条两端带有矩形，SQUARE，矩形
//        canvas?.drawLine(100f, 600f, 400f, 600f, mPaint);
//
//        mPaint.strokeWidth = 10f
//        mPaint.color = Color.BLACK
//        canvas?.drawLine(100f, 50f, 100f, 650f, mPaint);
//        canvas?.drawLine(400f, 50f, 400f, 650f, mPaint);

        mPaint.setStyle(Paint.Style.STROKE);

        val mPath: Path
        mPath = Path();

        mPath.moveTo(100f, 100f)// 结合处为锐角
        mPath.lineTo(450f, 100f)
        mPath.lineTo(100f, 300f)
        mPaint.strokeJoin = Paint.Join.MITER
        canvas?.drawPath(mPath, mPaint);

        mPath.reset()

        mPath.moveTo(100f, 400f)// 结合处为直线
        mPath.lineTo(450f, 400f)
        mPath.lineTo(100f, 600f)
        mPaint.strokeJoin = Paint.Join.BEVEL
        canvas?.drawPath(mPath, mPaint);

        mPath.reset()

        mPath.moveTo(100f, 700f) // 结合处为圆弧
        mPath.lineTo(450f, 700f)
        mPath.lineTo(100f, 900f)
        mPaint.strokeJoin = Paint.Join.ROUND
        canvas?.drawPath(mPath, mPaint);

        mPaint.strokeWidth = 10f
        mPaint.color = Color.BLACK
        canvas?.drawLine(100f, 50f, 100f, 950f, mPaint);
        canvas?.drawLine(450f, 50f, 450f, 950f, mPaint);
    }
}
