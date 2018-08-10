package com.youdianpinwei.myapplication

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.util.AttributeSet
import android.view.MotionEvent
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
 */
class CusView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    var paint: TextPaint
    var bitmap: Bitmap
    private val WIDTH = 60
    private val HEIGHT = 60
    private val COUNT = (WIDTH + 1) * (HEIGHT + 1)

    private val mVerts = FloatArray(COUNT * 2)
    private val mOrig = FloatArray(COUNT * 2)

    private val mMatrix = Matrix()
    private val mInverse = Matrix()

    init {
        paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        bitmap = BitmapFactory.decodeResource(resources, R.mipmap.mn)
        isFocusable = true

        val w = bitmap.getWidth().toFloat()
        val h = bitmap.getHeight().toFloat()
        // 根据图片高宽和网格数量,填充mVerts内容
        var index = 0
        for (y in 0..HEIGHT) {
            val fy = h * y / HEIGHT
            for (x in 0..WIDTH) {
                val fx = w * x / WIDTH
                setXY(mVerts, index, fx, fy)
                setXY(mOrig, index, fx, fy)
                index += 1
            }
        }

        mMatrix.setTranslate(10f, 10f)
        mMatrix.invert(mInverse)
    }

    private fun setXY(array: FloatArray, index: Int, x: Float, y: Float) {
        array[index * 2 + 0] = x
        array[index * 2 + 1] = y
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(-0x333334)

        canvas?.concat(mMatrix)
        canvas?.drawBitmapMesh(bitmap, WIDTH, HEIGHT, mVerts, 0, null, 0, null)
    }

    private fun warp(cx: Float, cy: Float) {
        val K = 10000f
        val src = mOrig
        val dst = mVerts
        var i = 0
        while (i < COUNT * 2) {
            val x = src[i + 0]
            val y = src[i + 1]
            val dx = cx - x
            val dy = cy - y
            val dd = dx * dx + dy * dy
            val d = Math.sqrt(dd.toDouble()).toFloat()
            var pull = K / (dd + 0.000001f)
            pull /= d + 0.000001f
            if (pull >= 1) {
                dst[i + 0] = cx
                dst[i + 1] = cy
            } else {
                dst[i + 0] = x + dx * pull
                dst[i + 1] = y + dy * pull
            }
            i += 2
        }
    }

    private var mLastWarpX = -9999
    private var mLastWarpY: Int = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val pt = floatArrayOf(event.x, event.y)
        mInverse.mapPoints(pt)

        val x = pt[0].toInt()
        val y = pt[1].toInt()
        if (mLastWarpX != x || mLastWarpY != y) {
            mLastWarpX = x
            mLastWarpY = y
            warp(pt[0], pt[1])
            invalidate()
        }
        return true
    }

}