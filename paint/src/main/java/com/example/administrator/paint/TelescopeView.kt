package com.example.administrator.paint

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.os.Build
import android.support.annotation.RequiresApi


/**
 * Created by Administrator on 2018/7/5.
 */
class TelescopeView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {

    // var:可变
    // val:只读
    // private 该类中可用
    // public 项目中可用
    // internal 在同一module可用
    // lateinit 只能用var类型,不能用在可空的属性上和java的基本类型上,可以在任何位置初始化并且可以初始化多次,有支持（反向）域;
    // lazy{}只能用在val类型,在第一次被调用时就被初始化，想要被改变只能重新定义;

    private var mPaint: Paint? = null
    private var mShader: Shader? = null

    private var mBitmap: Bitmap? = null
    private var mBitmapBG: Bitmap? = null

    private var dx: Int = -1
    private var dy: Int = -1

    init {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        if (mBitmap == null) {
            var mBitmapOtions = BitmapFactory.Options();
            mBitmapOtions.inJustDecodeBounds = true;

            BitmapFactory.decodeResource(resources, R.mipmap.bg, mBitmapOtions);
            if (mBitmapOtions.outWidth > w && mBitmapOtions.outHeight > h) {
                val scalewidth = mBitmapOtions.outWidth / w
                val scaleheigh = mBitmapOtions.outHeight / h
                mBitmapOtions.inSampleSize = if (scaleheigh > scalewidth) scaleheigh else scalewidth
            }
            mBitmapOtions.inJustDecodeBounds = false;
            mBitmap = BitmapFactory.decodeResource(resources, R.mipmap.bg, mBitmapOtions)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                dx = event.x.toInt()
                dy = event.y.toInt()
                postInvalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                dx = event.x.toInt()
                dy = event.y.toInt()
            }
            MotionEvent.ACTION_UP -> {
                dx = -1
                dy = -1
            }
        }
        postInvalidate()
        return super.onTouchEvent(event)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawColor(Color.GREEN)
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        /**
         *  注意用作BitmapShader的bitmap别太大,如果特别大的话,会发生卡顿,所以使用下面的这段代码有以下效果:
         *  1. 压缩图片,可以防止图片太大发生卡顿,同时也可以防止有的界面看不到
         *  2. 拉伸图片防止出现空白
         *
         *  需要注意的是,使用Shader后,Shader的图形是从控件的左上角显示的.我们滑动的时候,只是将我们想要看到的显示出来.
         *  mShader = BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
         *  mPaint!!.shader = mShader
         *  canvas?.drawRect(400f,400f,900f,900f,mPaint);
         *
         *  如果想要移动Shader上的位置,必须使用Shader.setLocalMatrix(Matrix)
         */

        if (mBitmapBG == null) {
            mBitmapBG = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            val mCanvas = Canvas(mBitmapBG)
            mCanvas.drawBitmap(mBitmap, null, Rect(0, 0, width, height), mPaint)
        }
//        if (dx != -1 && dy != -1) {
//            mShader = BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
//            mPaint!!.shader = mShader
//            canvas?.drawCircle(dx.toFloat() - 80, dy.toFloat(), 100.toFloat(), mPaint)
//            canvas?.drawCircle(dx.toFloat() + 80, dy.toFloat(), 100.toFloat(), mPaint)
//        }


//        canvas?.translate(100f, 100f)
//        /* 绘制旧的Path */
//        val srcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        srcPaint.style = Paint.Style.STROKE
//        srcPaint.shader = BitmapShader(mBitmapBG, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT)
//        srcPaint.setStrokeWidth(20f)
//        // 设置虚线
//        srcPaint.pathEffect = DashPathEffect(floatArrayOf(10f, 5f), 10f)
//        //设置矩形
//        val srcPath = Path()
//        srcPath.addRect(0f, 0f, 300f, 300f, Path.Direction.CCW)
//        canvas?.drawPath(srcPath, srcPaint)
//
//        /* 绘制新的Path */
//        canvas?.translate(0f, 350f)
//        val desPath = Path()
//        srcPaint.getFillPath(srcPath, desPath)
//        val desPaint = Paint(Paint.ANTI_ALIAS_FLAG)
//        setLayerType(LAYER_TYPE_SOFTWARE, null)
//        desPaint.color = Color.parseColor("#ff0000")
//        desPaint.style = Paint.Style.STROKE
//        canvas?.drawPath(desPath, desPaint)


        val text = "这是一条正常的文本"
        val srcPaint = Paint()
        srcPaint.setTextSize(100f)
        srcPaint.letterSpacing = 1f
        srcPaint.color = Color.BLACK
        srcPaint.style = Paint.Style.FILL
        canvas?.drawText(text, 50f, 100f, srcPaint)
        //获取文本路径
        canvas?.translate(0f, 150f)
        val desPath = Path()
        val desPaint = Paint()
        desPaint.setTextSize(100f)
        desPaint.color = Color.RED
        desPaint.style = Paint.Style.STROKE
        srcPaint.getTextPath(text, 0, text.length, 50f, 100f, desPath)
        canvas?.drawPath(desPath, desPaint)
    }
}