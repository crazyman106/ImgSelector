package com.example.administrator.colorfilter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.util.AttributeSet
import android.view.View

class LoadingProgressView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : View(context, attrs, defStyleAttr) {
    private val mSRCPaint: Paint

    private val mPaint: Paint
    private val mTextPaint: Paint
    private val mCanvas: Canvas
    private val mBitmap: Bitmap
    private var y: Int = 0
    private var x: Int = 0

    private val mMode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
    private val bgBitmap: Bitmap? = null
    private val mPath: Path
    private var isLeft: Boolean = false
    //    private int y;
    private var mWidth: Int = 0
    private var mHeight: Int = 0
    private var mPercent = 50

    init {
        mPaint = Paint()
        mPaint.strokeWidth = 10f


        mPath = Path()
        mPaint.isAntiAlias = true
        mPaint.color = Color.parseColor("#88f54548")

        mSRCPaint = Paint()
        mSRCPaint.isAntiAlias = true
        mSRCPaint.color = Color.parseColor("#88dddddd")

        mTextPaint = Paint()
        mTextPaint.isAntiAlias = true

        // 创建一个bitmap位图,高宽:500,500;颜色参数配置:8888ARGB四通道32位
        mBitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
        // 生成一个新的位图
        mCanvas = Canvas(mBitmap)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == View.MeasureSpec.EXACTLY) {
            mWidth = widthSize
        }

        if (heightMode == View.MeasureSpec.EXACTLY) {
            mHeight = heightSize
        }

        y = mHeight
        setMeasuredDimension(mWidth, mHeight)

    }

    override fun onDraw(canvas: Canvas) {
        if (x > 50) {
            isLeft = true
        } else if (x < 0) {
            isLeft = false
        }
        if (isLeft) {
            x = x - 1
        } else {
            x = x + 1
        }
        mPath.reset()
        y = ((1 - mPercent / 100f) * mHeight).toInt()
        mPath.moveTo(0f, y.toFloat())
        mPath.cubicTo((100 + x * 2).toFloat(), (50 + y).toFloat(), (100 + x * 2).toFloat(), (y - 50).toFloat(), mWidth.toFloat(), y.toFloat())
        mPath.lineTo(mWidth.toFloat(), mHeight.toFloat())
        mPath.lineTo(0f, mHeight.toFloat())
        mPath.close()
        //清除掉图像 不然path会重叠
        // 给生产的位图填充颜色
        mBitmap.eraseColor(Color.parseColor("#00000000"))
        // 在画布的位图上画一个圆形
        mCanvas.drawCircle((mWidth / 2).toFloat(), (mHeight / 2).toFloat(), (mWidth / 2).toFloat(), mSRCPaint)
        // 设置混合模式
        mPaint.xfermode = mMode
        // 在画布上画贝塞尔曲线
        mCanvas.drawPath(mPath, mPaint)
        mPaint.xfermode = null

        // 将上述生成的bitmap画到屏幕上
        canvas.drawBitmap(mBitmap, 0f, 0f, null)
        val str = mPercent.toString() + ""
        mTextPaint.textSize = 80f
        val txtLength = mTextPaint.measureText(str)
        canvas.drawText(str, mWidth / 2 - txtLength / 2, (mHeight / 2 + 15).toFloat(), mTextPaint)
        mTextPaint.textSize = 60f
        canvas.drawText("%", (mWidth / 2 + 70).toFloat(), (mHeight / 2 + 15).toFloat(), mTextPaint)
        postInvalidateDelayed(10)
    }

    fun setPercent(percent: Int) {
        mPercent = percent
    }

}
