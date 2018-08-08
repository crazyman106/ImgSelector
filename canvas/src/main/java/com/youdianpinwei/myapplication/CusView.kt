package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.View


/**
 * @author lijunjie on 2018/8/6 0006.
 * @description
 * /*        var width: Int = bitmap.width
var hegith: Int = bitmap.height
var radius: Int
when {
width < hegith -> radius = width / 2
width > hegith -> radius = hegith / 2
else -> radius = width / 2
}
var path = Path()
// path.addCircle(radius.toFloat(), radius.toFloat(), radius.toFloat(), Path.Direction.CW)
path.addRoundRect(RectF(0f, 0f, width.toFloat(), hegith.toFloat()), 30f, 30f, Path.Direction.CW)
canvas?.clipPath(path, Region.Op.INTERSECT)
canvas?.drawBitmap(bitmap, 0f, 0f, paint)*/

/*paint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
paint.color = Color.RED
canvas?.clipRect(50, 20, 400, 200)
canvas?.drawRect(0f, 0f, 600f, 300f, paint)
paint.setShader(null)
canvas?.restore()*/
 */
class CusView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var paint: Paint
    var bitmap: Bitmap
    var mMatrix: Matrix = Matrix()

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.refer_madness)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        paint.textSize = 20f
        paint.shader = LinearGradient(0f, 0f, 80f, 30f, Color.RED, Color.GREEN, Shader.TileMode.CLAMP)
        paint.setShadowLayer(5f, 2f, 3f, Color.parseColor("#77000000"))
        canvas?.drawText("android开发", 0, 3, 5f, 30f, paint)

/*        paint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        var rectF1 = RectF(0f, 0f, 240f, 160f)
        canvas?.drawRoundRect(rectF1, 40f, 40f, paint)

        paint.shader = null
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.strokeWidth = 1f
        paint.color = Color.BLACK
        canvas?.drawPoint(250f,40f,paint)
        canvas?.drawPoint(250f,80f,paint)
        canvas?.drawPoint(250f,120f,paint)*/

        /* canvas?.translate(5f, 5f)
         canvas?.matrix=mMatrix
         canvas?.concat(mMatrix)
         canvas?.drawBitmap(bitmap, 0f, 0f, paint)
         */
        /*  paint.strokeWidth = 3f
          var rectF1 = RectF(0f, 0f, 100f, 100f)
          paint.style = Paint.Style.FILL_AND_STROKE
          paint.color = Color.RED
          canvas?.drawRect(rectF1, paint)
          paint.style = Paint.Style.STROKE
          paint.color = Color.WHITE
          canvas?.drawArc(rectF1, 0f, 120f, true, paint)

          paint.style = Paint.Style.FILL_AND_STROKE
          var rectF2 = RectF(0f, 150f, 100f, 250f)
          paint.color = Color.RED
          canvas?.drawRect(rectF2, paint)
          paint.color = Color.WHITE
          paint.style = Paint.Style.STROKE
          canvas?.drawArc(rectF2, 0f, 120f, false, paint)


          var rectF3 = RectF(150f, 0f, 250f, 100f)
          paint.style = Paint.Style.FILL_AND_STROKE
          paint.color = Color.RED
          canvas?.drawRect(rectF3, paint)
          paint.color = Color.WHITE
          canvas?.drawArc(rectF3, 0f, 120f, true, paint)

          var rectF4 = RectF(150f, 150f, 250f, 250f)
          paint.color = Color.RED
          canvas?.drawRect(rectF4, paint)
          paint.color = Color.WHITE
          canvas?.drawArc(rectF4, 0f, 120f, false, paint)*/
    }

    public fun click() {
//        mMatrix.setTranslate(10f, 20f)
        mMatrix.postTranslate(10f, 20f)
        // mMatrix.preTranslate(10f, 20f)
        invalidate()
    }
}