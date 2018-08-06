package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View


/**
 * @author lijunjie on 2018/8/6 0006.
 * @description
 */
class CusView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var paint: Paint
    var bitmap: Bitmap

    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.refer_madness)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        canvas?.save()
        canvas?.translate(5f, 5f)
        var width: Int = bitmap.width
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
        canvas?.drawBitmap(bitmap, 0f, 0f, paint)
        canvas?.restore()
    }

    /**
     * 获得Bitmap
     */
//    private fun getBitmap(): Bitmap {
//        // 创建空白bitmap
//        val bm = Bitmap.createBitmap(measuredWidth, measuredHeight, Bitmap.Config.ARGB_8888)
//        // 获取drawable
//        val drawable = drawable
//        // 将bitmap画到canvas上
//        val drawableCanvas = Canvas(bm)
//        // 计算缩放
//        val drawablewidth = drawable.intrinsicWidth
//        val drawableheight = drawable.intrinsicHeight
//        val width = measuredWidth
//        val height = measuredHeight
//        scale = Math.min(width * 1.0f / drawablewidth, height * 1.0f / drawableheight)
//        // 缩放
//        drawable.setBounds(0, 0, (drawablewidth * scale).toInt(), (drawableheight * scale).toInt())
//        // 将drawable画到canvas上的bitmap中
//        drawable.draw(drawableCanvas)
//        return bm
//    }

}