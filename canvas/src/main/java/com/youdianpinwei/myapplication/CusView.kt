package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
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
        canvas?.translate(5f, 5f)
        canvas?.concat(mMatrix);
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
    }

    public fun click() {
//        mMatrix.setTranslate(10f, 20f)
        mMatrix.postTranslate(10f, 20f)
        // mMatrix.preTranslate(10f, 20f)
        invalidate()
    }
}