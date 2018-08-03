package com.youdianpinwei.canva

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author lijunjie on 2018/8/3 0003.
 * @description
 */
class ConstTstView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 1) : View(context, attrs, defStyleAttr) {

    var paint: Paint

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.save()
        canvas?.translate(5f, 5f)
        paint.setColor(Color.parseColor("#00ff00"))
        canvas?.drawRect(RectF(0f, 0f, 300f, 300f), paint)
        canvas?.drawCircle(300f, 300f, 150f, paint)

        paint.setColor(Color.parseColor("#ff0000"))

        canvas?.clipRect(RectF(0f, 0f, 300f, 300f))

        val mPath = Path()
        mPath.addCircle(300f, 300f, 150f, Path.Direction.CCW)
        canvas?.clipPath(mPath, Region.Op.INTERSECT)
        canvas?.drawRect(RectF(0f, 0f, Integer.MAX_VALUE.toFloat(), Integer.MAX_VALUE.toFloat()), paint)
        canvas?.restore()
    }

}