package com.fengzi.footmark.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.fengzi.footmark.R;
import com.fengzi.footmark.test.BitmapUtils;
import com.fengzi.footmark.test.ScreenUtils;

public class StyleTextView extends View implements View.OnTouchListener {
    public static final int ORIENTATION_HORIZONTAL = 0;
    public static final int ORIENTATION_VERTICAL = 1;

    protected String text = "点击设置";
    private float mScale = 1f;
    private Context mContext;
    private float mSpacing = 0f;
    protected int padding = 50;
    private float mTextSize = 50;
    private int mTextColor = 0xffff0000;
    private int mBgColor = 0x00ffffff;
    private int mStrokeColor = 0xffffffff;

    private BgStyle mBgStyle;
    protected boolean isShowStroke = true;
    private boolean isMultiLine;
    private int orientation = ORIENTATION_HORIZONTAL;
    private int strokeWidth = 5;
    protected TextPaint mTextPaint;
    private int viewWidth, viewHeight;

    protected Paint mStrokePaint, mBgPaint;

    private Paint.Align mTextAlign = Paint.Align.CENTER;



    public static final int DEFAULT_STROKE_SIZE = 3;

    public int strokeRealColor = Color.BLACK;//画笔实际颜色
    public float strokeSize = DEFAULT_STROKE_SIZE;

    public StyleTextView(Context context) {
        this(context, null, 0);
    }

    public StyleTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StyleTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mStrokePaint = new Paint();
        mStrokePaint.setAntiAlias(true);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(mStrokeColor);
        mStrokePaint.setStrokeWidth(strokeWidth);

        mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBgPaint.setColor(mBgColor);




        strokePaint = new Paint();
        strokePaint.setAntiAlias(true);
        strokePaint.setDither(true);
        strokePaint.setColor(strokeRealColor);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeJoin(Paint.Join.ROUND);
        strokePaint.setStrokeCap(Paint.Cap.ROUND);
        strokePaint.setStrokeWidth(strokeSize);

        invalidate();

        initDrawValue();
        setBgStyle(BgStyle.Rect);
    }



    public int getPadding() {
        return padding;
    }

    private Path bgPath;
    private Rect rect;
    private RectF rectF, left, right;

    private void initDrawValue() {
        bgPath = new Path();
        rect = new Rect();
        rectF = new RectF();
        left = new RectF();
        right = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getBackground() == null) {
            int w = 0, h = 0;
            int[] wh = getWidthAndHeight();
            viewWidth = wh[0];
            viewHeight = wh[1];
        } else {
            viewWidth = getBackground().getIntrinsicWidth();
            viewHeight = getBackground().getIntrinsicHeight();
        }
        corners[0] = 0;
        corners[1] = 0;
        corners[2] = viewWidth;
        corners[3] = 0;
        corners[4] = viewWidth;
        corners[5] = viewHeight;
        corners[6] = 0;
        corners[7] = viewHeight;
        corners[8] = viewWidth / 2;
        corners[9] = viewHeight / 2;
        setMeasuredDimension(viewWidth, viewHeight);
    }

    public int checkTextSize(String text, float width, float height) {
        TextPaint tp = new TextPaint();
        tp.setTypeface(mTextPaint.getTypeface());
        int textSize = 0;
        for (int i = 0; i < 200; i++) {
            tp.setTextSize(i);
            int w = (int) tp.measureText(text);
            int h = (int) (tp.descent() - tp.ascent());
            if ((w > width - 5 && w < width)) {
                textSize = i;
                break;
            }
        }
        return textSize;
    }

    private int[] getWidthAndHeight() {
        int[] wh = new int[2];
        int w = 0, h = 0;
        int lineCount = 1;

        if (text.contains("\n")) {
            isMultiLine = true;
        } else {
            isMultiLine = false;
        }

        if (isMultiLine) {
            String[] texts = text.split("\n");
            int fontHeight = (int) (-mTextPaint.ascent() + mTextPaint.descent());
            if (orientation == ORIENTATION_HORIZONTAL) {
                for (int i = 0; i < texts.length; i++) {
                    String textItem = texts[i];
                    int itemLength = (int) mTextPaint.measureText(textItem);
                    if (itemLength > w) {
                        w = itemLength;
                    }
                }
                lineCount = texts.length;
                h = (int) mSpacing * (lineCount - 1);
            } else {
                for (int i = 0; i < texts.length; i++) {
                    String textItem = texts[i];
                    int itemCount = textItem.length();
                    if (itemCount > lineCount) {
                        lineCount = itemCount;
                    }
                }
                w = (int) mTextPaint.measureText("国") * texts.length + (int) mSpacing * (texts.length - 1);
            }
            w = w + getPaddingLeft() + getPaddingRight();
            h = h + fontHeight * lineCount + getPaddingTop() + getPaddingBottom();
        } else {
            if (orientation == ORIENTATION_HORIZONTAL) {
                w = (int) (((int) mTextPaint.measureText(text) + getPaddingLeft() + getPaddingRight()) * 1);
                h = (int) (((int) (-mTextPaint.ascent() + mTextPaint.descent()) + getPaddingTop() + getPaddingBottom()) * 1);
            } else {

                for (int i = 0; i < text.length(); i++) {
                    String s = text.substring(i, i + 1);
                    int ww = (int) mTextPaint.measureText(s);
                    if (ww > w) {
                        w = ww;
                    }
                }

                w = (int) ((w + getPaddingLeft() + getPaddingRight()) * mScale);
                h = (int) (((int) ((-mTextPaint.ascent() + mTextPaint.descent()) * text.length()) + getPaddingTop() + getPaddingBottom()) * mScale);
            }
        }
        wh[0] = w;
        wh[1] = h;
        return wh;
    }

    public void setScale(float scale) {
        mScale = mScale * scale;
        setScaleX(mScale);
        setScaleY(mScale);
    }

    public void setScaleByTextSize(float f) {
        if (isMultiLine && orientation == ORIENTATION_HORIZONTAL) {
            mSpacing = mSpacing * f;
        }
        mTextSize = mTextSize * f;
        mTextPaint.setTextSize(mTextSize);
        requestLayout();
    }


    protected void setNewPadding(int newPadding) {
        padding = newPadding;
        setPadding(newPadding, newPadding - 5, newPadding, newPadding - 5);
        requestLayout();
    }

    public TextPaint getTextPaint() {
        return mTextPaint;
    }

    public void initText() {
        text = "点击设置";
        requestLayout();
    }

    public int[] setText(String text) {
        this.text = text;
        int[] wh = getWidthAndHeight();
        if (wh[0] == viewWidth && wh[1] == viewHeight) {
            invalidate();
        } else {
            requestLayout();
        }
        return wh;
    }

    public String getText() {
        return text;
    }


    public void setSpacing(float mSpacing) {
        this.mSpacing = mSpacing;
        requestLayout();
    }

    public float getSpacing() {
        return mSpacing;
    }

    public void setTextSize(float mTextSize) {
        this.mTextSize = mTextSize;
        mTextPaint.setTextSize(mTextSize);
        requestLayout();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }


    public int getTextColor() {
        return mTextColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        mBgPaint.setColor(mBgColor);
        invalidate();
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgStyle(BgStyle mBgStyle) {
        this.mBgStyle = mBgStyle;
        if (mBgStyle == BgStyle.TwoRect) {
            setPadding(padding * 3, padding, padding * 3, padding);
        } else {
            setPadding(padding, padding, padding, padding);
            invalidate();
        }
    }

    public BgStyle getBgStyle() {
        return mBgStyle;
    }

    public void setFont(String path) {
        Typeface typeFace;
        if (!path.equalsIgnoreCase("")) {
            typeFace = Typeface.createFromAsset(mContext.getAssets(), path);
        } else {
            typeFace = Typeface.SERIF;
        }
        mTextPaint.setTypeface(typeFace);
        requestLayout();
    }

    public void setTextAlign(Paint.Align mTextAlign) {
        this.mTextAlign = mTextAlign;
        invalidate();
    }

    float[] corners = new float[10];

    protected void drawStroke(Canvas canvas) {
        canvas.drawRect(getPaddingLeft() / 2, getPaddingTop() / 2, getWidth() - getPaddingRight() / 2, getHeight() - getPaddingBottom() / 2, mStrokePaint);
    }

    public void showStroke() {
        isShowStroke = true;
        invalidate();
    }

    public void hideStroke() {
        isShowStroke = false;
        invalidate();
    }

    public boolean isShowStroke() {
        return isShowStroke;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(rect,strokePaint);

        if (mBgStyle == BgStyle.Rect) {
            rect.set(strokeWidth * 2, strokeWidth * 2, getWidth() - strokeWidth * 2, getHeight() - strokeWidth * 2);
            canvas.drawRect(rect, mBgPaint);
        } else if (mBgStyle == BgStyle.RectF) {
            rectF.set(strokeWidth * 2, strokeWidth * 2, getWidth() - strokeWidth * 2, getHeight() - strokeWidth * 2);
            canvas.drawRoundRect(rectF, 20, 20, mBgPaint);
        } else {
            int sideLength = viewHeight / 3;
            bgPath.reset();
            bgPath.moveTo(strokeWidth * 2, strokeWidth * 2);
            bgPath.lineTo(strokeWidth * 2, viewHeight - strokeWidth * 2 - sideLength);
            bgPath.lineTo(strokeWidth * 2 + sideLength, viewHeight - strokeWidth * 2);
            bgPath.lineTo(viewWidth - strokeWidth * 2, viewHeight - strokeWidth * 2);
            bgPath.lineTo(viewWidth - strokeWidth * 2, strokeWidth * 2 + sideLength);
            bgPath.lineTo(viewWidth - strokeWidth * 2 - sideLength, strokeWidth * 2);
            bgPath.close();

            canvas.drawPath(bgPath, mBgPaint);
            left.set(strokeWidth * 2, viewHeight - strokeWidth * 2 - sideLength * 2,
                    strokeWidth * 2 + sideLength * 2, viewHeight - strokeWidth * 2);

            right.set(viewWidth - strokeWidth * 2 - sideLength * 2, strokeWidth * 2,
                    viewWidth - strokeWidth * 2, strokeWidth * 2 + sideLength * 2);

            canvas.drawArc(left, 90, 90, true, mBgPaint);
            canvas.drawArc(right, 270, 90, true, mBgPaint);
        }

        if (isShowStroke) {
            drawStroke(canvas);
        }

        int textWidth = (int) mTextPaint.measureText(text);
        int textHeight = (int) (-mTextPaint.ascent() + mTextPaint.descent());
        int l = (canvas.getWidth() - textWidth) / 2;
        int t = (canvas.getHeight() - textHeight) / 2;

        if (isMultiLine) {
            String[] texts = text.split("\n");
            int lineCount = texts.length;
            if (orientation == ORIENTATION_HORIZONTAL) {
                int baseX;
                if (mTextAlign == Paint.Align.CENTER) {
                    baseX = canvas.getWidth() / 2;
                } else if (mTextAlign == Paint.Align.LEFT) {
                    baseX = getPaddingLeft();
                } else {
                    baseX = canvas.getWidth() - getPaddingRight();
                }
                mTextPaint.setTextAlign(mTextAlign);

                for (int i = 0; i < lineCount; i++) {
                    String textItem = texts[i];
                    canvas.save();
                    canvas.translate(0, i * textHeight + mSpacing * i);
                    canvas.drawText(textItem, baseX, getPaddingTop() + textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
                    canvas.restore();
                }
            } else {
                mTextPaint.setTextAlign(Paint.Align.CENTER);
                int itemWidth = (int) mTextPaint.measureText("国");
                for (int i = 0; i < lineCount; i++) {
                    String textItem = texts[i];
                    for (int j = 0; j < textItem.length(); j++) {
                        String s = textItem.substring(j, j + 1);
                        canvas.save();
                        canvas.translate(getPaddingLeft() + (lineCount - 1 - i) * (itemWidth + mSpacing), getPaddingTop() + j * textHeight);
                        canvas.drawText(s, itemWidth / 2, textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
                        canvas.restore();
                    }
                }
            }
        } else {
            mTextPaint.setTextAlign(Paint.Align.CENTER);
            if (orientation == ORIENTATION_HORIZONTAL) {
                int textY = canvas.getHeight() / 2 + (int) (textHeight / 2 - mTextPaint.descent());
                canvas.drawText(text, canvas.getWidth() / 2, textY, mTextPaint);
            } else {
                for (int i = 0; i < text.length(); i++) {
                    String s = text.substring(i, i + 1);
                    canvas.save();
                    canvas.translate(0, getPaddingTop() + i * textHeight);
                    canvas.drawText(s, canvas.getWidth() / 2, textHeight / 2 + (int) (textHeight / 2 - mTextPaint.descent()), mTextPaint);
                    canvas.restore();
                }
            }
        }


    }

    public boolean isAboveScale(int x, int y) {
        int scaleX = getRight() - padding;
        int titlebarH = 50;
        int scaleY = titlebarH + getBottom() - padding;
        return (x > scaleX && x < getRight() && y > scaleY && y < getBottom() + titlebarH);
    }

    public boolean isAboveDelete(int x, int y) {
        int titlebarH = (int) 50;
        return (x > getLeft() && x < getLeft() + padding && y > getTop() + titlebarH && y < getTop() + padding + titlebarH);
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public void changeOrientation() {
        if (orientation == ORIENTATION_HORIZONTAL) {
            orientation = ORIENTATION_VERTICAL;
        } else {
            orientation = ORIENTATION_HORIZONTAL;
        }
        requestLayout();
    }

    int[] location = new int[2];
    public float downX, downY, preX, preY, curX, curY;
    public int drawDensity = 2;//绘制密度,数值越高图像质量越低、性能越好

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return true;
    }



    /**
     * 获取p1到p2的线段的长度
     *
     * @return
     */
    public double getVectorLength(PointF vector) {
        return Math.sqrt(vector.x * vector.x + vector.y * vector.y);
    }

    public Path strokePath;
    public Paint strokePaint;



    public enum BgStyle {
        Rect, RectF, TwoRect
    }
}