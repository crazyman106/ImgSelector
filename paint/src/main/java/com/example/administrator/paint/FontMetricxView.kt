package com.example.administrator.paint

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * @author lijunjie on 2018/7/2 0002.
 * @description
 */

class FontMetricxView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    internal var mPaint: Paint
    internal var text = "ac"
    internal var mFontMetrics: Paint.FontMetrics
    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.textSize = 80f
        mPaint.color = Color.RED
        mFontMetrics = mPaint.fontMetrics
        Log.e("FontMetricxView", "ascent：" + mFontMetrics.ascent)
        Log.e("FontMetricxView", "top：" + mFontMetrics.top)
        Log.e("FontMetricxView", "leading：" + mFontMetrics.leading)
        Log.e("FontMetricxView", "descent：" + mFontMetrics.descent)
        Log.e("FontMetricxView", "bottom：" + mFontMetrics.bottom)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, 0f, Math.abs(mFontMetrics.bottom), mPaint)
        //        canvas.drawText(text, 0, (Math.abs(mFontMetrics.bottom) + Math.abs(mFontMetrics.top)) * 2, mPaint);
        Log.e("FontMetricxView", "width：" + width)
        Log.e("FontMetricxView", "height：" + height)
    }
}
