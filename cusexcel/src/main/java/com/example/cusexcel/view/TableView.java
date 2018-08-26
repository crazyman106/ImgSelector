package com.example.cusexcel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.cusexcel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:表格控件
 */
public class TableView extends View {

    /**
     * 单元格基准宽度，设权重的情况下，为最小单元格宽度
     */
    private float columnWidth;
    private float rowHeight;
    private float width, height;

    private float dividerWidth;
    private int dividerColor;
    private float textSize;
    private int textColor;
    private List<String> contentTxt = new ArrayList<>();
    private List<Rect> rects = new ArrayList<>();
    private List<LinePoint> linePoints = new ArrayList<>();
    private Rect rectDelIcon = new Rect();
    private Rect rectControlIcon = new Rect();
    private Bitmap delBitmap, controlBitmap;
    private int padding = 50;
    private int lineStrokeWidth = 3;

    private int rowCount;
    private int columnCount;

    private Paint paintTxt, paintBorder, paintInsideLine;
    DashPathEffect dashPathEffect;


    public TableView(Context context) {
        super(context);
        init(null);
    }

    public TableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        paintTxt = new Paint();
        paintBorder = new Paint();
        paintInsideLine = new Paint();
        paintTxt.setAntiAlias(true);
        paintTxt.setTextAlign(Paint.Align.LEFT);
        paintTxt.setTextSize(20);

        dashPathEffect = new DashPathEffect(new float[]{4, 4}, 0);
        paintInsideLine.set(paintTxt);
        paintInsideLine.setStyle(Paint.Style.STROKE);
        paintInsideLine.setColor(Color.BLACK);
        paintInsideLine.setStrokeWidth(lineStrokeWidth);

        paintBorder.set(paintTxt);
        paintBorder.setStyle(Paint.Style.STROKE);
        paintBorder.setColor(Color.BLACK);
        paintBorder.setStrokeWidth(lineStrokeWidth);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TableView);
            dividerWidth = typedArray.getDimensionPixelSize(R.styleable.TableView_dividerWidth, 1);
            dividerColor = typedArray.getColor(R.styleable.TableView_dividerColor, Color.parseColor("#E1E1E1"));
            textSize = typedArray.getDimensionPixelSize(R.styleable.TableView_textSize, Util.dip2px(getContext(), 10));
            textColor = typedArray.getColor(R.styleable.TableView_textColor, Color.parseColor("#999999"));
            typedArray.recycle();
        } else {
            dividerWidth = 1;
            dividerColor = Color.parseColor("#E1E1E1");
            textSize = Util.dip2px(getContext(), 10);
            textColor = Color.parseColor("#999999");
        }
        initTableSize();
       /* setOnTouchListener(new com.example.cusexcel.view.OnDoubleClickListener(new com.example.cusexcel.view.OnDoubleClickListener.DoubleClickCallback() {
            @Override
            public void onDoubleClick(MotionEvent event) {
                for (int i = 0; i < rects.size(); i++) {
                    if (rects.get(i).contains((int) event.getX(), (int) event.getY())) {
                        if (rectDelIcon.contains((int) event.getX(), (int) event.getY()) || rectControlIcon.contains((int) event.getX(), (int) event.getY())) {
                            return;
                        }
                        if (doubleClickListener != null) {
                            doubleClickListener.onDoubleClick(i);
                            Log.e("doubleclick", i + "");
                        }
                        break;
                    }
                }
                invalidate();
            }
        }));*/

        delBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_delete);
        controlBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_move);
        padding = Math.min(delBitmap.getWidth(), controlBitmap.getWidth()) / 2;
    }

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 1000;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_DOWN == event.getAction()) {//按下
            count++;
            if (1 == count) {
                firstClick = System.currentTimeMillis();//记录第一次点击时间
            } else if (2 == count) {
                secondClick = System.currentTimeMillis();//记录第二次点击时间
                if (secondClick - firstClick < totalTime) {//判断二次点击时间间隔是否在设定的间隔时间之内
                    for (int i = 0; i < rects.size(); i++) {
                        if (rects.get(i).contains((int) event.getX(), (int) event.getY())) {
                            if (rectDelIcon.contains((int) event.getX(), (int) event.getY()) || rectControlIcon.contains((int) event.getX(), (int) event.getY())) {
                                count = 0;
                                firstClick = 0;
                                return super.onTouchEvent(event);
                            }
                            if (doubleClickListener != null) {
                                doubleClickListener.onDoubleClick(i);
                                Log.e("doubleclick", i + "");
                            }
                            break;
                        }
                    }
                    invalidate();
                    count = 0;
                    firstClick = 0;
                } else {
                    firstClick = secondClick;
                    count = 1;
                }
                secondClick = 0;
            }
        }
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectDelIcon.set(0, 0, padding * 2, padding * 2);
        rectControlIcon.set(w - padding * 2, h - padding * 2, w, h);
        //计算宽度及列宽
        width = w - padding * 2;
        height = h - padding * 2;
        columnWidth = (width - 2 * dividerWidth) / columnCount;
        rowHeight = (height - 2 * dividerWidth) / rowCount;
    }

    private void calculateFormLength() {
        //未设置宽度，根据控件宽度来确定最小单元格宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setPadding(padding, padding, padding, padding);
        drawBitmap(canvas);
        calculateColumns();
        drawFramework(canvas);
        calculateRects();
        drawContent(canvas);
    }

    private void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(delBitmap, 0, 0, paintTxt);
        canvas.drawBitmap(controlBitmap, getWidth() - controlBitmap.getWidth(), getHeight() - controlBitmap.getHeight(), paintTxt);
    }

    private void calculateColumns() {
        linePoints.clear();
        // 添加竖线
        LinePoint linePoint;
        for (int i = 1; i < columnCount; i++) {
            linePoint = new LinePoint();
            linePoint.setX1(i * columnWidth + padding);
            linePoint.setY1(0f + padding);
            linePoint.setX2(i * columnWidth + padding);
            linePoint.setY2(height + padding);
            linePoints.add(linePoint);
        }
        // 添加横线
        for (int i = 1; i < rowCount; i++) {
            linePoint = new LinePoint();
            linePoint.setX1(0f + padding);
            linePoint.setY1(i * rowHeight + padding);
            linePoint.setX2(width + padding);
            linePoint.setY2(i * rowHeight + padding);
            linePoints.add(linePoint);
        }
    }

    /**
     * 画整体表格框架
     */
    private void drawFramework(Canvas canvas) {
        canvas.drawRect(padding, padding, width + padding, height + padding, paintBorder);
        Path path;
        LinePoint linePoint;
        // 画竖线
        for (int i = 0; i < linePoints.size(); i++) {
            linePoint = linePoints.get(i);
            path = new Path();
            path.moveTo(linePoint.getX1(), linePoint.getY1());
            path.lineTo(linePoint.getX2(), linePoint.getY2());
            canvas.drawPath(path, paintInsideLine);
        }
    }

    /**
     * 画内容
     */
    private void drawContent(Canvas canvas) {
        for (int i = 0; i < columnCount * rowCount; i++) {
            canvas.drawText(contentTxt.get(i), (float) getTextStartX(i % columnCount * columnWidth, paintTxt, contentTxt.get(i)),
                    (float) getTextBaseLine(i / columnCount * rowHeight, paintTxt), paintTxt);
        }
    }

    /**
     * 初始化行列数
     */
    private void initTableSize() {
        rowCount = 4;
        columnCount = 2;
        for (int i = 0; i < rowCount * columnCount; i++) {
            if (i == 0) {
                contentTxt.add("双击输入文字");
            } else {
                contentTxt.add("");
            }
        }
    }

    private float getTextBaseLine(float rowStart, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (rowStart + (rowStart + rowHeight) - fontMetrics.bottom - fontMetrics.top) / 2 + padding;
    }

    private float getTextStartX(float columnStart, Paint paint, String content) {
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        if (TextUtils.isEmpty(content)) {
            return columnStart + 4 * paintBorder.getStrokeWidth() + padding;
        }
        if (rect.width() > columnWidth) {
            return columnStart + 4 * paintBorder.getStrokeWidth() + padding;
        } else {
            return columnStart + (columnWidth - rect.width()) / 2 + padding;
        }
    }

    // 统计表格Rect
    private void calculateRects() {
        rects.clear();
        Rect rect;
        for (int i = 0; i < columnCount * rowCount; i++) {
            rect = new Rect();
            rect.left = (int) (i % columnCount * columnWidth + padding);
            rect.top = (int) (i / columnCount * rowHeight + padding);
            rect.right = (int) (i % columnCount * columnWidth + columnWidth + padding);
            rect.bottom = (int) (i / columnCount * rowHeight + rowHeight + padding);
            rects.add(rect);
        }
    }

    public void setContent(int index, String content) {
        contentTxt.set(index, content);
        invalidate();
    }

    public interface OnDoubleClickListener {
        void onDoubleClick(int index);
    }

    private OnDoubleClickListener doubleClickListener;

    public void setOnDoubleClickListener(OnDoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    public void addColumn() {
        columnCount += 1;
        for (int i = 1; i < columnCount * rowCount + 1; i++) {
            if (i % columnCount == 0) {
                contentTxt.add(i - 1, "");
            }
        }
        columnWidth = (width - 2 * dividerWidth) / columnCount;
        invalidate();
    }

    public void subtractColumn() {
        columnCount -= 1;
        int pos = contentTxt.size();
        for (int i = pos; i > 0; i--) {
            if (i % rowCount == 0) {
                contentTxt.remove(i - 1);
            }
        }
        columnWidth = (width - 2 * dividerWidth) / columnCount;
        invalidate();
    }

    public void addRow() {
        rowCount += 1;
        for (int i = 0; i < columnCount; i++) {
            contentTxt.add("");
        }
        rowHeight = (height - 2 * dividerWidth) / rowCount;
        invalidate();
    }

    public void subtractRow() {
        rowCount -= 1;
        for (int i = 0; i < columnCount; i++) {
            contentTxt.remove(contentTxt.size() - 1);
        }
        rowHeight = (height - 2 * dividerWidth) / rowCount;
        invalidate();
    }

    public void setOuterFull() {
        paintBorder.setPathEffect(null);
        lineStrokeWidth = 5;
        paintBorder.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setOuterDash() {
        paintBorder.setPathEffect(dashPathEffect);
        lineStrokeWidth = 5;
        paintBorder.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setInnerFull() {
        paintInsideLine.setPathEffect(null);
        lineStrokeWidth = 5;
        paintInsideLine.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setInnerDash() {
        paintInsideLine.setPathEffect(dashPathEffect);
        lineStrokeWidth = 5;
        paintInsideLine.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setOuterColor(int color) {
        paintBorder.setColor(color);
        invalidate();
    }

    public void setInnerColor(int color) {
        paintInsideLine.setColor(color);
        invalidate();
    }
}

