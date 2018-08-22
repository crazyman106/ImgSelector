package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by Administrator on 2018/8/20.
 */
class BezierView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attributeSet, defStyleAttr) {

    var mPaint: Paint
    var centerX: Int = 0
    var centerY: Int = 0
    var start: PointF
    var end: PointF
    var control: PointF


    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.BLACK
        mPaint.strokeWidth = 2f
        mPaint.style = Paint.Style.STROKE
        mPaint.textSize = 20f

        start = PointF(0f, 0f)
        end = PointF(0f, 0f)
        control = PointF(0f, 0f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        centerX = w / 2
        centerY = h / 2

        start.x = (centerX - 200).toFloat()
        start.y = centerY.toFloat()
        end.x = (centerX + 200).toFloat()
        end.y = centerY.toFloat()
        control.x = centerX.toFloat()
        control.y = centerY.toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        control.x = event?.x!!
        control.y = event?.y!!
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // 绘制数据点和控制点
        mPaint.color = Color.RED
        mPaint.strokeWidth = 10f
        canvas?.drawPoint(start.x, start.y, mPaint)
        canvas?.drawPoint(end.x, end.y, mPaint)
        canvas?.drawPoint(control.x, control.y, mPaint)

        // 绘制辅助线
        mPaint.strokeWidth = 2f
        canvas?.drawLine(start.x, start.y, control.x, control.y, mPaint)
        canvas?.drawLine(end.x, end.y, control.x, control.y, mPaint)

        // 绘制贝塞尔曲线
        mPaint.color = Color.GREEN
        mPaint.strokeWidth = 2f

        var path = Path()
        path.moveTo(start.x, start.y)
        path.quadTo(control.x, control.y, end.x, end.y)

        canvas?.drawPath(path, mPaint)
    }


}