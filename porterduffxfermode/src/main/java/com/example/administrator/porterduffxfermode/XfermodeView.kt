package com.example.administrator.porterduffxfermode

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

/**
 * @author lijunjie on 2018/6/26 0026.
 * @description
 */
class XfermodeView : View {

    constructor(mContext: Context, mPaint: Paint, mSource: Bitmap, mDst: Bitmap) : super(mContext) {
        this.mPaint = mPaint
        this.mSource = mSource
        this.mDst = mDst
    }

    constructor(context: Context?, attrs: AttributeSet?, mPaint: Paint, mSource: Bitmap, mDst: Bitmap) : super(context, attrs) {
        this.mPaint = mPaint
        this.mSource = mSource
        this.mDst = mDst
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, mPaint: Paint, mSource: Bitmap, mDst: Bitmap) : super(context, attrs, defStyleAttr) {
        this.mPaint = mPaint
        this.mSource = mSource
        this.mDst = mDst
    }


    lateinit var mPaint: Paint
    // 源图
    lateinit var mSource: Bitmap
    // 目标图
    lateinit var mDst: Bitmap
    // 过渡模式
    var mXfermode: Xfermode? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        mDst = getCircleBitmap()
        mSource = getRetangleBitmap()
        /*
         * 将绘制操作保存到新的图层（更官方的说法应该是离屏缓存）
         */
        val sc = canvas?.saveLayer(0f, 0f, width.toFloat(), height.toFloat(), null, Canvas.ALL_SAVE_FLAG)

        // 先绘制dis目标图
        canvas?.drawBitmap(mDst, 0f, 0f, mPaint)

        // 设置混合模式
        mPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)

        // 再绘制src源图
        canvas?.drawBitmap(mSource, 0f, 0f, mPaint)

        // 还原混合模式
        mPaint.xfermode = null

        // 还原画布
        canvas?.restoreToCount(sc!!)
    }

    /**
     * 设置混合模式
     */
    fun setXfermode(mode: PorterDuff.Mode) {
        mXfermode = PorterDuffXfermode(mode)
        invalidate()
    }

    private fun getRetangleBitmap(): Bitmap {
        /**
         * bm1 在bitmap上面画正方形
         */
        val rectangle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val c1 = Canvas(rectangle)
        val p1 = Paint(Paint.ANTI_ALIAS_FLAG)
        p1.color = resources.getColor(R.color.colorAccent)
        /**
         * 设置透明
         */
        c1.drawARGB(0, 0, 0, 0)
        c1.drawRect(0f, 0f, 200f, 200f, p1)
        return rectangle
    }

    private fun getCircleBitmap(): Bitmap {
        /**
         * bm 在bitmap上面画圆
         */
        val circle = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888)
        val c = Canvas(circle)
        /**
         * 设置透明
         */
        c.drawARGB(0, 0, 0, 0)
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = resources.getColor(R.color.colorPrimary)
        c.drawCircle(100f, 100f, 100f, p)
        return circle
    }
}