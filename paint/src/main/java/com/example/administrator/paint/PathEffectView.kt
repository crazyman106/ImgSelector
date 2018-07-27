package com.example.administrator.mPaint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author lijunjie on 2018/7/27 0027.
 * @description
 */
class PathEffectView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    lateinit var mPaint: Paint

    init {
        initParams()
    }

    fun initParams() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.flags = Paint.DITHER_FLAG
        mPaint.color = Color.BLACK;
        mPaint.strokeWidth = 4f;
        mPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        /* mPath.addCircle(5f, 5f, 10f, Path.Direction.CCW);*/


        val path = Path()

        path.moveTo(0f, 0f);

        // 定义路径的各个点
        for (i in 1..40) {
            path.lineTo(i * 35f, (Math.random() * 100).toFloat());
        }
        canvas?.drawPath(path, mPaint)
        //圆角特效
        canvas?.translate(0f, 200f)
        val cornerPathEffect = CornerPathEffect(100f)
        mPaint.setPathEffect(cornerPathEffect)
        canvas?.drawPath(path, mPaint)

        //虚线特效
        canvas?.translate(0f, 200f)
        val dashPathEffect = DashPathEffect(floatArrayOf(50f, 4f, 18f, 12f), 0f)
        mPaint.setPathEffect(dashPathEffect)
        canvas?.drawPath(path, mPaint)

        //利用ComposePathEffect先应用圆角特效,再应用虚线特效
        canvas?.translate(0f, 200f)
        val composePathEffect = ComposePathEffect(dashPathEffect, cornerPathEffect)
        mPaint.setPathEffect(composePathEffect)
        canvas?.drawPath(path, mPaint)

        //利用ComposePathEffect先应用虚线特效,再应用圆角特效
        canvas?.translate(0f, 200f)
        val composePathEffect1 = ComposePathEffect(cornerPathEffect, dashPathEffect)
        mPaint.setPathEffect(composePathEffect1)
        canvas?.drawPath(path, mPaint)



        //利用SumPathEffect,分别将圆角和虚线应用于原始路径,然后将生成的两条特效路径合并
        canvas?.translate(0f, 200f)
        mPaint.setStyle(Paint.Style.STROKE)
        val sumPathEffect = SumPathEffect(cornerPathEffect, dashPathEffect)
        mPaint.setPathEffect(sumPathEffect)
        canvas?.drawPath(path, mPaint)

        //利用SumPathEffect,分别将圆角和虚线应用于原始路径,然后将生成的两条特效路径合并
        canvas?.translate(0f, 200f)
        mPaint.setStyle(Paint.Style.STROKE)
        val sumPathEffect1 = SumPathEffect(dashPathEffect, cornerPathEffect)
        mPaint.setPathEffect(sumPathEffect1)
        canvas?.drawPath(path, mPaint)

/*        var mPath = Path();
        mPath.moveTo(0f, 0f);
        mPath.lineTo(20f, 0f);
        mPath.lineTo(10f, Math.sqrt(300.toDouble()).toFloat());
        mPath.close();*/
/*        canvas?.translate(0f, 200f)
        canvas?.drawPath(path, mmPaint);

        mmPaint.color = Color.CYAN
        canvas?.translate(0f, 200f)
        mmPaint.setPathEffect(PathDashPathEffect(mPath, 35f, 2f, PathDashPathEffect.Style.MORPH))
        canvas?.drawPath(path, mmPaint)


        mmPaint.color = Color.GREEN
        canvas?.translate(0f, 200f)
        mmPaint.setPathEffect(PathDashPathEffect(mPath, 35f, phase, PathDashPathEffect.Style.ROTATE))
        canvas?.drawPath(path, mmPaint)

        mmPaint.color = Color.BLUE
        canvas?.translate(0f, 200f)
        mmPaint.setPathEffect(PathDashPathEffect(mPath, 25f, phase, PathDashPathEffect.Style.TRANSLATE))
        canvas?.drawPath(path, mmPaint)*/
/*        phase++
        invalidate()*/
        /*canvas?.translate(0f, 150f)
        mmPaint.color = Color.RED
        mmPaint.pathEffect = DiscretePathEffect(2f, 8f)
        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 150f)
        mmPaint.color = Color.GREEN
        mmPaint.pathEffect = DiscretePathEffect(2f, 16f)
        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 150f)
        mmPaint.color = Color.BLUE
        mmPaint.pathEffect = DiscretePathEffect(8f, 2f)
        canvas?.drawPath(path, mmPaint)
*/

/*        path.moveTo(100f, 100f)
        path.lineTo(800f, 100f)

        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 100f)
        mmPaint.color = Color.RED
        mmPaint.setPathEffect(DashPathEffect(floatArrayOf(40f, 10f, 80f, 20f), 0f))
        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 100f)
        mmPaint.color = Color.RED
        mmPaint.setPathEffect(DashPathEffect(floatArrayOf(40f, 10f, 80f, 20f), 10f))
        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 100f)
        mmPaint.color = Color.RED
        mmPaint.setPathEffect(DashPathEffect(floatArrayOf(40f, 10f, 80f, 20f), 20f))
        canvas?.drawPath(path, mmPaint)

        canvas?.translate(0f, 100f)
        mmPaint.color = Color.RED
        mmPaint.setPathEffect(DashPathEffect(floatArrayOf(40f, 10f, 80f, 20f), 30f))
        canvas?.drawPath(path, mmPaint)*/
        /*   path.lineTo(400f, 100f)
           path.lineTo(700f, 1000f)

           canvas?.drawPath(path, mmPaint)

           mmPaint.setColor(Color.RED)
           mmPaint.setPathEffect(CornerPathEffect(100f))
           canvas?.drawPath(path, mmPaint)

           mmPaint.setColor(Color.YELLOW)
           mmPaint.setPathEffect(CornerPathEffect(200f))
           canvas?.drawPath(path, mmPaint)

           mmPaint.setColor(Color.BLACK)
           mmPaint.setPathEffect(CornerPathEffect(360f))
           canvas?.drawPath(path, mmPaint)*/
    }


    /*   private fun getStampPath(): Path {

           path.moveTo(0f, 20f);
           path.lineTo(10f, 0f);
           path.lineTo(20f, 20f);
           path.close();


           return path;
       }*/
}