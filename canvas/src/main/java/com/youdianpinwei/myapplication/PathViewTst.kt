package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View

/**
 * @author lijunjie on 2018/8/16 0016.
 * @description
 */
class PathViewTst @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var mPaint: Paint
    var mPath: Path;

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPath = Path()
        mPath.addCircle(40f, 40f, 45f, Path.Direction.CCW)
        mPath.addCircle(80f, 80f, 45f, Path.Direction.CCW)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /* val paint = mPaint
         canvas?.drawColor(-0x333334)
         canvas?.translate(20f, 20f)
         paint.setAntiAlias(true)

         showPath(canvas!!, 0, 0, Path.FillType.WINDING, paint)
         showPath(canvas!!, 160, 0, Path.FillType.EVEN_ODD, paint)
         showPath(canvas!!, 0, 160, Path.FillType.INVERSE_WINDING, paint)
         showPath(canvas!!, 160, 160, Path.FillType.INVERSE_EVEN_ODD, paint)*/

        mPaint.style = Paint.Style.FILL
        mPaint.color = Color.RED
        canvas?.translate((width / 2).toFloat(), (height / 2).toFloat())
        val path = Path()
        // 叠加两个不同半径圆形组成自相交图形用来展示不同Direction下的非零环绕规则
        path.addCircle(-250f, 0f, 100f, Path.Direction.CW);
        path.addCircle(-250f, 0f, 200f, Path.Direction.CCW)

        path.addCircle(250f, 0f, 100f, Path.Direction.CCW);
        path.addCircle(250f, 0f, 200f, Path.Direction.CCW)

        path.fillType = Path.FillType.WINDING
        canvas?.drawPath(path, mPaint)
    }

    private fun showPath(canvas: Canvas, x: Int, y: Int, ft: Path.FillType,
                         paint: Paint) {
        /*    canvas.save()
            canvas.translate(x.toFloat(), y.toFloat())
    //        canvas.clipRect(0, 0, 120, 120)
    //        canvas.drawColor(Color.WHITE)
            mPath.fillType = ft
            canvas.drawPath(mPath, paint)
            canvas.restore()*/
    }
}