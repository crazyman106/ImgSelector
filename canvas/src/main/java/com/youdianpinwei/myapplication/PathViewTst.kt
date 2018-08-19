package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.os.Build
import android.support.annotation.RequiresApi
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
        mPaint.color = Color.parseColor("#FF8C00")
        mPaint.textSize = 17f
        mPath = Path()
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.translate(250f, 0f)

        val path1 = Path()
        val path2 = Path()
        val pathOpResult = Path()

        path1.addCircle(-80f, 0f, 100f, Path.Direction.CW)
        path2.addCircle(80f, 0f, 100f, Path.Direction.CW)

        pathOpResult.op(path1,Path.Op.DIFFERENCE)
        canvas?.translate(0f, 200f)
        canvas?.drawPath(pathOpResult, mPaint)

        pathOpResult.op(path1, Path.Op.REVERSE_DIFFERENCE)
        canvas?.translate(0f, 300f)
        canvas?.drawPath(pathOpResult, mPaint)

        pathOpResult.op(path1,  Path.Op.INTERSECT)
        canvas?.translate(0f, 300f)
        canvas?.drawPath(pathOpResult, mPaint)

        pathOpResult.op(path1,  Path.Op.UNION)
        canvas?.translate(0f, 300f)
        canvas?.drawPath(pathOpResult, mPaint)

        pathOpResult.op(path1, Path.Op.XOR)
        canvas?.translate(0f, 300f)
        canvas?.drawPath(pathOpResult, mPaint)

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