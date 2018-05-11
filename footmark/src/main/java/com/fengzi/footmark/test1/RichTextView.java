package com.fengzi.footmark.test1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.fengzi.footmark.R;

/**
 * @author lijunjie on 2018/5/11 0011.
 * @description
 */

public class RichTextView extends View {
    public static final float MAX_SCALE_SIZE = 10;//3.2f;
    public static final float MIN_SCALE_SIZE = 0;//0.6f;
    private Bitmap mControllerBitmap, mDeleteBitmap;
    private float mControllerWidth, mControllerHeight, mDeleteWidth, mDeleteHeight;
    private boolean mDrawController = true;
    private float[] mOriginPoints;
    private float[] mPoints;
    private Paint mPaint, mBorderPaint;
    private Matrix mMatrix;
    private boolean mInController, mInMove;
    private boolean mInDelete = false;
    /* 触摸位置到左上角位置的中心 */
    private PointF t2sMiddle = new PointF();
    /* 当前视图的边框属性值(left,top,right,bottom) */
    private RectF mViewRect;
    /* 内容区域 */
    private RectF mContentRect;
    private RectF mOriginContentRect;

    private float mStickerScaleSize = 1.0f;


    public RichTextView(Context context) {
        this(context, null);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RichTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mMatrix = new Matrix();


        dm = getResources().getDisplayMetrics();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4.0f);
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(14);

        mBorderPaint = new Paint(mPaint);
        mBorderPaint.setColor(Color.parseColor("#000000"));//#B2ffffff
        mBorderPaint.setShadowLayer(dpToPx(2.0f), 0, 0, Color.parseColor("#33000000"));

        mControllerBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm_edittext_translate);
        mControllerWidth = mControllerBitmap.getWidth();
        mControllerHeight = mControllerBitmap.getHeight();

        mDeleteBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.tm_edittext_delete);
        mDeleteWidth = mDeleteBitmap.getWidth();
        mDeleteHeight = mDeleteBitmap.getHeight();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOriginPoints == null) {
            float px = w;
            float py = h;
            mOriginPoints = new float[]{0, 0, px, 0, px, py, 0, py, px / 2, py / 2};
            mOriginContentRect = new RectF(0, 0, px, py);

            mPoints = new float[10];

            mStickerScaleSize = 0f;

//            float transtLeft = ((float) dm.widthPixels - px) / 2;
//            float transtTop = ((float) dm.heightPixels - py) / 2;
//            mMatrix.postTranslate(transtLeft, transtTop);

            mContentRect = new RectF();

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        String txt = text;
        Rect rect = new Rect();
        mPaint.getTextBounds(txt, 0, txt.length(), rect);
        int width = (int) (mControllerWidth * 2 + rect.right - rect.left);
        int height = (int) (mControllerHeight * 2 + rect.bottom - rect.top);
        setMeasuredDimension(width, height);

    }


    private DisplayMetrics dm;
    String text = "12345678912";

    @Override
    protected void onDraw(final Canvas canvas) {
//        super.onDraw(canvas);

        mMatrix.mapPoints(mPoints, mOriginPoints);
        mMatrix.mapRect(mContentRect, mOriginContentRect);




        canvas.drawText(text, 0, mControllerHeight / 3, mPaint);

        if (mDrawController) {//&& isFocusable()
            canvas.drawLine(mPoints[0] + mControllerWidth / 2, mPoints[1] + mControllerHeight / 2, mPoints[2] - mControllerWidth / 2, mPoints[3] + mControllerHeight / 2, mBorderPaint);
            canvas.drawLine(mPoints[2] - mControllerWidth / 2, mPoints[3] + mControllerHeight / 2, mPoints[4] - mControllerWidth / 2, mPoints[5] - mControllerHeight / 2, mBorderPaint);
            canvas.drawLine(mPoints[4] - mControllerWidth / 2, mPoints[5] - mControllerHeight / 2, mPoints[6] + mControllerWidth / 2, mPoints[7] - mControllerHeight / 2, mBorderPaint);
            canvas.drawLine(mPoints[6] + mControllerWidth / 2, mPoints[7] - mControllerHeight / 2, mPoints[0] + mControllerWidth / 2, mPoints[1] + mControllerHeight / 2, mBorderPaint);


            canvas.drawBitmap(mControllerBitmap, mPoints[4] - mControllerWidth, mPoints[5] - mControllerHeight, mBorderPaint);
            canvas.drawBitmap(mDeleteBitmap, mPoints[0], mPoints[1], mBorderPaint);
        }
    }

    private float mLastPointX, mLastPointY;

    private boolean isMove;
    private float curScale;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mViewRect == null) {
            mViewRect = new RectF(0, 0, getMeasuredWidth(), getMeasuredHeight());
        }
        float x = event.getX();
        float y = event.getY();
        int action = event.getActionMasked();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (isInController(x, y)) {
                    mInController = true;
                    mLastPointX = x;
                    mLastPointY = y;
                    midPointToStartPoint(x, y);
                } else if (isInDelete(x, y)) {
                    mInDelete = true;
                } else if (mContentRect.contains(x, y)) {
                    setShowDrawController(true);
                    mLastPointY = y;
                    mLastPointX = x;
                    mInMove = true;
                    isMove = false;
                } else {
                    setShowDrawController(false);
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mInController) { // 按住按钮旋转,缩放
                    mMatrix.postRotate(rotation(event), t2sMiddle.x, t2sMiddle.y);


                    float nowLength = caculateLength(mPoints[4], mPoints[5]);
                    float touchLength = caculateLength(event.getX(), event.getY());
                    if ((float) Math.sqrt((nowLength - touchLength) * (nowLength - touchLength)) > 0.0f) {
                        float scale = touchLength / nowLength;
                        float nowsc = mStickerScaleSize * scale;
                        if (nowsc >= MIN_SCALE_SIZE && nowsc <= MAX_SCALE_SIZE) {
                            mMatrix.postScale(scale, scale, t2sMiddle.x, t2sMiddle.y);
                            mStickerScaleSize = nowsc;
                        }
                    }
                    invalidate();
                    mLastPointX = x;
                    mLastPointY = y;
                    break;
                }
                if (mInMove) { // 按住内容拖拽
                    float cX = x - mLastPointX;
                    float cY = y - mLastPointY;
                    mInController = false;

                    //判断手指抖动距离 加上isMove判断 只要移动过 都是true
                    if (!isMove && Math.abs(cX) < 0.5f
                            && Math.abs(cY) < 0.5f) {
                        isMove = false;
                    } else {
                        isMove = true;
                    }
                    mMatrix.postTranslate(cX, cY);

                    postInvalidate();
                    mLastPointX = x;
                    mLastPointY = y;
                    break;
                }
                break;
            case MotionEvent.ACTION_UP:
                mInController = false;
                if (isInDelete(x, y) && mInDelete) {
                    mInDelete = false;
                    doDeleteView();
                }
                return true;
            case MotionEvent.ACTION_CANCEL:
                mLastPointX = 0;
                mLastPointY = 0;
                mInController = false;
                mInMove = false;
                mInDelete = false;
                break;
            default:
                break;
        }


        return true;
    }

    /**
     * 判断触摸点是否在控制缩放和旋转的图标坐标中
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInController(float x, float y) {
        float rx = mPoints[4];
        float ry = mPoints[5];
        RectF rectF = new RectF(rx - mControllerWidth, ry - mControllerHeight, rx, ry);
        if (rectF.contains(x, y)) {
            return true;
        }
        return false;
    }

    /**
     * 判断触摸点是否在删除图标坐标中
     *
     * @param x
     * @param y
     * @return
     */
    private boolean isInDelete(float x, float y) {
        float rx = mPoints[0];
        float ry = mPoints[1];
        RectF rectF = new RectF(rx, ry, rx + mDeleteWidth, ry + mDeleteHeight);
        if (rectF.contains(x, y)) {
            return true;
        }
        return false;
    }

    /**
     * 触摸的位置和图片左上角位置的中点
     *
     * @param x
     * @param y
     */
    private void midPointToStartPoint(float x, float y) {
        float[] arrayOfFloat = new float[9];
        mMatrix.getValues(arrayOfFloat);
        float f1 = 0.0f * arrayOfFloat[0] + 0.0f * arrayOfFloat[1] + arrayOfFloat[2];
        float f2 = 0.0f * arrayOfFloat[3] + 0.0f * arrayOfFloat[4] + arrayOfFloat[5];
        float f3 = f1 + x;
        float f4 = f2 + y;
        t2sMiddle.set(f3 / 2, f4 / 2);
    }

    private float rotation(MotionEvent event) {
        float originDegree = calculateDegree(mLastPointX, mLastPointY);
        float nowDegree = calculateDegree(event.getX(), event.getY());
        curDegree = nowDegree - originDegree;
        return nowDegree - originDegree;
    }

    private float curDegree;

    private float calculateDegree(float x, float y) {
        double delta_x = x - t2sMiddle.x;
        double delta_y = y - t2sMiddle.y;
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    private float caculateLength(float x, float y) {
        float ex = x - t2sMiddle.x;
        float ey = y - t2sMiddle.y;
        return (float) Math.sqrt(ex * ex + ey * ey);
    }

    private void doDeleteView() {

    }

    public void setShowDrawController(boolean show) {
        mDrawController = show;
        invalidate();
    }


    private float dpToPx(float pt) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pt, getResources().getDisplayMetrics());
    }
}
