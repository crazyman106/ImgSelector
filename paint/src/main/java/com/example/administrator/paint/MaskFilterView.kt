package com.example.administrator.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * Created by Administrator on 2018/7/8.
 */
class MaskFilterView @JvmOverloads constructor(mContext: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(mContext, attrs, defStyleAttr) {

    lateinit var mPaint: Paint
    private var mMaskFilter: MaskFilter? = null

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.RED
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        canvas?.drawColor(Color.parseColor("#7700ff00"))

        canvas?.translate(40f, 40f)
        canvas?.drawRect(0f, 0f, 200f, 200f, mPaint)

        mMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.NORMAL)
        mPaint.maskFilter = mMaskFilter
        canvas?.drawRect(0f, 250f, 200f, 450f, mPaint)

        mMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.SOLID)
        mPaint.maskFilter = mMaskFilter
        canvas?.drawRect(0f, 500f, 200f, 700f, mPaint)

        mMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.OUTER)
        mPaint.maskFilter = mMaskFilter
        canvas?.drawRect(0f, 750f, 200f, 950f, mPaint)

        mMaskFilter = BlurMaskFilter(20f, BlurMaskFilter.Blur.INNER)
        mPaint.maskFilter = mMaskFilter
        canvas?.drawRect(0f, 1000f, 200f, 1200f, mPaint)
    }

}