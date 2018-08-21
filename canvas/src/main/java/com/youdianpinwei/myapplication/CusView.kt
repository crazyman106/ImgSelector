package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextPaint
import android.util.AttributeSet
import android.util.Log
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

/*paint.setShader(BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
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
/* paint.textSize = 38f
paint.color = Color.RED
var text: String = "我是一个android程序员"
canvas?.drawTextRun(text, 0, text.length, 0, text.length, 0f, 100f, false, paint)
canvas?.drawTextRun(text, 0, text.length, 0, text.length, 0f, 150f, true, paint)*/
/* paint.shader = LinearGradient(0f, 0f, 80f, 30f, Color.RED, Color.GREEN, Shader.TileMode.CLAMP)
paint.setShadowLayer(5f, 2f, 3f, Color.parseColor("#77000000"))
canvas?.drawText("android开发", 0, 3, 5f, 30f, paint)*/

/*        paint.textSize = 20f;
canvas?.translate(200f, 200f)
paint.strokeWidth = 5f
paint.style = Paint.Style.FILL_AND_STROKE
paint.color = Color.GREEN
canvas?.drawPaint(paint)*/


/*        var floatArray = floatArrayOf(0f, 0f, 10f, 400f, 10f, 10f, 20f, 410f, 20f, 20f, 30f, 420f, 30f, 30f, 40f, 430f)
canvas?.drawLines(floatArray, 4, 12, paint)

var floatArray1 = floatArrayOf(60f, 0f, 70f, 400f, 70f, 10f, 80f, 410f, 80f, 20f, 90f, 420f, 90f, 30f, 100f, 430f)
canvas?.drawLines(floatArray1, paint)*/

/*    canvas?.drawCircle(0f, 0f, 100f, paint)
paint.shader = null
for (i in 0 until 12) {
canvas?.drawLine(0f, 80f, 0f, 95f, paint)
canvas?.drawText((i + 1).toString(), -10f, 75f, paint)
canvas?.rotate(30f)
}*/

public fun click() {
// mMatrix.setTranslate(10f, 20f)
mMatrix.postTranslate(10f, 20f)
// mMatrix.preTranslate(10f, 20f)
invalidate()
}
/*drawSomething(mPicture.beginRecording(200, 100))
mPicture.endRecording()
mDrawable = PictureDrawable(mPicture)

canvas?.drawColor(Color.WHITE)
// 将picture记录的绘制内容按照原始大小绘制到canvas中
canvas?.drawPicture(mPicture)
// 将picture记录的绘制内容按照缩放大小绘制到canvas指定的边框中
canvas?.drawPicture(mPicture, RectF(0f, 100f, 100f, 200f))
canvas?.drawPicture(mPicture, RectF(0f, 200f, 800f, 300f))
// 给drawable指定一个边界,在调用draw()的时候时候,边框内的内容是可以绘制的,在绘制的时候不会缩放内容,而是剪切出对应大小的内容.
mDrawable.setBounds(0, 300, 200, 400)
mDrawable.draw(canvas)

mDrawable.setBounds(0, 400, 100, 500)
mDrawable.draw(canvas)

mDrawable.setBounds(0, 500, 50, 600)
mDrawable.draw(canvas)*/
internal fun drawSomething(canvas: Canvas) {
val p = Paint(Paint.ANTI_ALIAS_FLAG)
p.color = -0x77010000
canvas.drawCircle(50f, 50f, 40f, p)
p.color = Color.GREEN
p.textSize = 30f
canvas.drawText("Pictures", 60f, 60f, p)
}
paint.color = Color.BLUE
paint.style = Paint.Style.FILL_AND_STROKE
// 在原始图层上画图
canvas?.drawCircle(200f, 200f, 80f, paint)

// 创建一个新的透明图层(图层的边界是:0,0,300,300)(如果在该图层的paint没有透明色值时,则使用0x77该透明度值,如果paint有透明色值,则使用该paint的透明值)
val layerAlpha: Int? = canvas?.saveLayerAlpha(0f, 0f, 300f, 300f, 0x77)
// 在透明图层上画图
canvas?.drawColor(Color.parseColor("#44ff0000"))
// paint.color = Color.parseColor("#55ff0000")
canvas?.drawCircle(150f, 150f, 80f, paint)

// 创建一个新的图层layerAlpha1高宽400x400
val layerAlpha1 = canvas?.saveLayerAlpha(0f, 0f, 400f, 400f, 0x255)
paint.color = Color.parseColor("#ff0000")
canvas?.drawRect(0f, 0f, 100f, 100f, paint)
//该图层上画矩形

// 还原layerAlpha1图层
layerAlpha1?.let { canvas?.restoreToCount(it) }
paint.color = Color.GREEN
// 在layerAlpha图层上继续画图
canvas?.drawCircle(250f, 250f, 80f, paint)
// 还原layerAlpha图层到原始图层上
layerAlpha?.let { canvas?.restoreToCount(it) }
// 在最初的图层上画图
canvas?.drawCircle(350f, 350f, 80f, paint)
var rect = Rect(0, 100, 200, 400)
canvas?.drawRect(rect, paint)
var rectF = RectF()
rectF.set(rect)
val reject = canvas?.quickReject(rectF, Canvas.EdgeType.AA)
Log.e("quickReject", reject.toString())
paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
canvas?.translate(10f, 10f)
path.addRoundRect(RectF(0f, 0f, 200f, 150f), 20f, 20f, Path.Direction.CCW)
canvas?.drawPath(path, paint)

paint.style = Paint.Style.STROKE
path.addRoundRect(RectF(220f, 0f, 420f, 150f), 30f, 30f, Path.Direction.CCW)
canvas?.drawPath(path, paint)


path.addRoundRect(RectF(0f, 200f, 200f, 350f), floatArrayOf(20f, 20f, 0f, 0f, 0f, 0f, 0f, 0f), Path.Direction.CCW)
canvas?.drawPath(path, paint)

path.addRoundRect(RectF(220f, 200f, 420f, 350f), floatArrayOf(20f, 20f, 20f, 20f,  0f, 0f, 0f, 0f), Path.Direction.CCW)
canvas?.drawPath(path, paint)

path.addRoundRect(RectF(440f, 200f, 640f, 350f), floatArrayOf(20f, 20f, 20f, 20f,20f, 20f, 0f, 0f), Path.Direction.CCW)
canvas?.drawPath(path, paint)
var path1 = Path();
var path2 = Path();
path1.addCircle(0f, 0f, 100f, Path.Direction.CCW)
path2.addCircle(100f, 100f, 50f, Path.Direction.CCW)
path.addPath(path1)
path.addPath(path2,-100f,-100f)
canvas?.drawPath(path, paint)
 */
class CusView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var paint: TextPaint
    var bitmap: Bitmap
    var path: Path;

    init {
        paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.mn)
        path = Path()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 2f

        paint.color = Color.RED
        path.addCircle(200f, 200f, 80f, Path.Direction.CCW)
        canvas?.drawPath(path, paint)

        paint.color = Color.GREEN
        var matrix: Matrix = Matrix()
        matrix.setScale(0.5f, 0.5f)
        path.transform(matrix)
        canvas?.drawPath(path, paint)

    }

}