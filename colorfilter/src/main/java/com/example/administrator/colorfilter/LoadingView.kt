package com.example.administrator.colorfilter

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Administrator on 2018/7/12.
 */
class LoadingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    private var mPaint: Paint

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var width = getMeasuredWidth();
        var height = getMeasuredHeight();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.LTGRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20f);
        canvas?.drawCircle(width / 2f, height / 2f, width / 2 - 20 / 2f, mPaint);
        var arcColors: IntArray = intArrayOf(
                0xFF09F68C.toInt(),
                0xFFB0F44B.toInt(),
                0xFFE8DD30.toInt(),
                0xFFF1CA2E.toInt(),
                0xFFFF902F.toInt(),
                0xFFFF6433.toInt(),
                0xFF09F68C.toInt())
        var sweepGradient = SweepGradient(getMeasuredWidth() / 2f, getMeasuredHeight() / 2f, arcColors, null);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(10f);
        var effects = DashPathEffect(floatArrayOf(2f, 4f, 8f, 16f), 20f);
        mPaint.setPathEffect(effects);
        mPaint.setShader(sweepGradient);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas?.drawArc(RectF(10f, 10f, width - 10f, height - 10f), -90f, 270f, false, mPaint);
    }


}