package com.example.cusexcel.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.cusexcel.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:表格控件
 * Creator:fengzi
 */
public class TableView extends View {

    // 单元格行列宽高
    private float columnWidth;
    private float rowHeight;
    // 表格实际宽度(因为表格左上角和右下角有bitmap,所以会存在一定的padding值,它会导致width和view.withd不相同,width = view.width-2*padding)
    private float width, height;

    /**
     * // 存放表格所有单元格的内容
     * 按照:
     * A,B,C
     * D,E,F
     * G,H,I
     * 这种顺序来存储
     */
    private List<String> contentTxt = new ArrayList<>();
    // 存储表格所有单元格的边界坐标
    private List<Rect> rects = new ArrayList<>();
    // 存储表格内边界所有行列线段的坐标
    private List<LinePoint> linePoints = new ArrayList<>();
    // 左上角删除图表的边界坐标
    private Rect rectDelIcon = new Rect();
    // 右下角缩放图表的边界坐标
    private Rect rectControlIcon = new Rect();
    // 删除图表,缩放图表
    private Bitmap delBitmap, controlBitmap;
    // view的padding,用来存放图片== math.max(bitmap.width/2)
    private int padding = 0;
    // 线宽度
    private int lineStrokeWidth = 3;

    // view的宽度,view的坐标中心点,用来缩放的时候使用
    private float originalWidth, originalCenterX, originalCenterY;
    // 屏幕宽度
    private float screenWidth;

    // 拉一个点击位置,rects.index
    private int lastClickPosition = -1;

    // 行列数
    private int rowCount;
    private int columnCount;

    // 文本,外边框,内边框画笔
    private Paint paintTxt, paintBorder, paintInsideLine;

    // 虚线效果实现类
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
            typedArray.recycle();
        } else {
        }
        initTableSize();
        delBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_delete);
        controlBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_move);
        padding = Math.max(delBitmap.getWidth(), controlBitmap.getWidth()) / 2;
        screenWidth = ScreenUtils.getWidth(getContext());
        scaleMatrix = new Matrix();
    }

    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间

    private long lastClickTime = 0;
    /**
     * 两次点击时间间隔，单位毫秒
     */
    private final int totalTime = 800;
    private boolean isCanScale = false;

    private float downX, downY;
    private boolean isCanTranslate = false;

    private float lastDownX, lastDownY;


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("scaleFactor", event.getRawY() + "");
                downX = event.getX();
                downY = event.getY();
                // 每次点击的时候先判断手指是不是在删除图表和缩放图表边界内,是的话,直接执行删除和缩放操作
                if (rectControlIcon.contains((int) event.getX(), (int) event.getY())) {
                    count = 0;
                    firstClick = 0;
                    secondClick = 0;
                    lastClickPosition = -1;
                    isCanScale = true;
                    break;
                }
                // 删除操作
                if (rectDelIcon.contains((int) event.getX(), (int) event.getY())) {
                    count = 0;
                    firstClick = 0;
                    lastClickPosition = -1;
                    if (delClickListener != null) {
                        delClickListener.onDelView();
                    }
                    break;
                }
                isCanTranslate = true;
                count++;
                // 第一次按下谈起时间,既第一次点击事件
                if (count == 1) {
                    lastClickTime = System.currentTimeMillis();//如果不是在删除和缩放图表边界内,记录第一次点击时间
                    Log.e("onClick", "onCLick1");
                    for (int i = 0; i < rects.size(); i++) {
                        if (rects.get(i).contains((int) event.getX(), (int) event.getY())) {
                            lastClickPosition = i;// 记录第一次点击表格位置
                            return true;
                        }
                    }
                } else if (count == 2) {
                    Log.e("onClick", "onCLick2");

                    long offsetTime = System.currentTimeMillis() - lastClickTime;
                    if (offsetTime < totalTime) {
                        count = 0;
                        for (int i = 0; i < rects.size(); i++) {
                            if (rects.get(i).contains((int) event.getX(), (int) event.getY()) && i == lastClickPosition) {
                                if (doubleClickListener != null && i == lastClickPosition) {
                                    doubleClickListener.onDoubleClick(i);
                                    Log.e("doubleClickListener", event.getX() + "--" + event.getY() + ";" + lastClickPosition + "--" + count);
                                    lastClickPosition = -1;
                                    lastClickTime = 0;
                                    isCanTranslate = false;
                                    count = 0;
                                    return true;
                                }
                                isCanTranslate = true;
                                lastClickTime = 0;
                                lastClickPosition = -1;
                            }
                            // 如果第二次点击是在删除和缩放图标内,这段代码也不能够执行,可以删掉这个if条件,但是还是加上了,
                            if (rectDelIcon.contains((int) event.getX(), (int) event.getY())
                                    || rectControlIcon.contains((int) event.getX(), (int) event.getY())) {
                                lastClickPosition = -1;
                                lastClickTime = 0;
                                isCanTranslate = false;
                                return true;
                            }
                        }
                    } else {
                        lastClickPosition = -1;
                        lastClickTime = 0;
                        count = 0;
                        isCanTranslate = true;
                    }
                    isCanTranslate = true;
                    count = 0;
                }
                lastDownX = event.getRawX();
                lastDownY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                lastClickPosition = -1;
                lastClickTime = 0;
                count = 0;
                if (isCanScale) { // 执行缩放操作
                   /* setScaleX(scaleFactorX(event));
                    setScaleY(scaleFactorY(event));*/
                    final float xDistance = event.getRawX() - lastDownX;
                    final float yDistance = event.getRawY() - lastDownY;
                    scaleX += xDistance / getWidth();
                    scaleY += yDistance / getHeight();
                    setScaleX(scaleX);
                    setScaleY(scaleY);
                    lastDownX = event.getRawX();
                    lastDownY = event.getRawY();
                }
                if (isCanTranslate) {
                    final float xDistance = event.getX() - downX;
                    final float yDistance = event.getY() - downY;
                    l = (int) (getLeft() + xDistance);
                    r = l + getWidth();
                    t = (int) (getTop() + yDistance);
                    b = t + getHeight();
                    this.layout(l, t, r, b);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                isCanScale = false;// 取消缩放
                isCanTranslate = false;
                downX = -1;
                downY = -1;
                break;
            default:
                break;
        }
        return true;
    }

    private float scaleX = 1f, scaleY = 1f;


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        super.onLayout(changed, left, top, right, bottom);
    }

    private int l, r, t, b;


    // 在该函数中获取view的高宽,同时根据计算出表格的高宽,单元格的高宽,以及缩放时需要的一些参数
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectDelIcon.set(0, 0, padding * 2, padding * 2);
        rectControlIcon.set(w - padding * 2, h - padding * 2, w, h);
        //计算宽度及列宽
        width = w - padding * 2;
        height = h - padding * 2;
        columnWidth = (width - 2 * lineStrokeWidth) / columnCount;
        rowHeight = (height - 2 * lineStrokeWidth) / rowCount;

        originalWidth = w;
        originalCenterX = w / 2;
        originalCenterY = h / 2;
        Log.e("onSizeChanged", originalCenterX + "--" + originalCenterY);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);//对表格和文本进行绘制
        setPadding(padding, padding, padding, padding);
        drawBitmap(canvas);// 绘制bitmap
        calculateColumns();// 计算表格内部行列线段的坐标
        drawFramework(canvas);//绘制表格(外边框和内部线段)
        calculateRects(); // 统计全部单元格的边界
        drawContent(canvas);// 绘制文本
    }

    private void drawBitmap(Canvas canvas) {
        canvas.drawBitmap(delBitmap, 0, 0, paintTxt);
        canvas.drawBitmap(controlBitmap, getWidth() - controlBitmap.getWidth(), getHeight() - controlBitmap.getHeight(), paintTxt);
    }

    private Matrix scaleMatrix;

    private void calculateColumns() {
        linePoints.clear(); // 为了在增删行列的时候去掉上一次的内容,
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
        // 画竖线,实际上可以通过drawLines()来一次画出全部数据,但是使用DashPathEffect对drawLines无效果,所以改用drawPath
        for (int i = 0; i < linePoints.size(); i++) {
            linePoint = linePoints.get(i);
            path = new Path();
            path.moveTo(linePoint.getX1(), linePoint.getY1());
            path.lineTo(linePoint.getX2(), linePoint.getY2());
            canvas.drawPath(path, paintInsideLine);
        }
    }

    /**
     * 绘制文本
     */
    private void drawContent(Canvas canvas) {
        Log.e("drawContent", contentTxt.size() + "--");
        for (int i = 0; i < columnCount * rowCount; i++) {
            canvas.drawText(contentTxt.get(i), (float) getTextStartX(i % columnCount * columnWidth, paintTxt, contentTxt.get(i)),
                    (float) getTextBaseLine(i / columnCount * rowHeight, paintTxt), paintTxt);
        }
    }

    /**
     * 初始化行列数,设置初始化文本内容
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

    /**
     * 根据FontMetrics获取文本高度,通过单元格的高宽和文本的高度计算baseline
     *
     * @param rowStart
     * @param paint
     * @return
     */
    private float getTextBaseLine(float rowStart, Paint paint) {
        final Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        return (rowStart + (rowStart + rowHeight) - fontMetrics.bottom - fontMetrics.top) / 2 + padding;
    }

    /**
     * 根据getTextBounds来计算文本的宽度,通过单元格的宽度和文本宽度来计算文本开始绘制的位置,从而达到文本居中显示的效果
     */
    private float getTextStartX(float columnStart, Paint paint, String content) {
        Rect rect = new Rect();
        paint.getTextBounds(content, 0, content.length(), rect);
        if (TextUtils.isEmpty(content)) {// 这个实际上可以不写,因为content为空的时候没有内容,所以不用居中显示
            return columnStart + 4 * paintBorder.getStrokeWidth() + padding;
        }
        if (rect.width() > columnWidth) { // 文本宽度大于单元格的宽度,从单元格开始位置绘制文字
            return columnStart + 4 * paintBorder.getStrokeWidth() + padding;
        } else {// 计算居中显示时绘制文本的开始位置
            return columnStart + (columnWidth - rect.width()) / 2 + padding;
        }
    }

    // 统计表格Rect,用来判断点击的表格位置
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
            Log.e("calculateRects", rect.toShortString());
        }
    }

    // 给制定的表格设置内容
    public void setContent(int index, String content) {
        contentTxt.set(index, content);
        invalidate();
    }

    // 双击监听事件
    public interface OnDoubleClickListener {
        void onDoubleClick(int index);
    }

    private OnDoubleClickListener doubleClickListener;


    public void setOnDoubleClickListener(OnDoubleClickListener doubleClickListener) {
        this.doubleClickListener = doubleClickListener;
    }

    // 删除监听事件
    // 可以把OnDelClickListener和OnDoubleClickListener合成一个
    public interface OnDelClickListener {
        void onDelView();
    }

    private OnDelClickListener delClickListener;

    public void setOnDelClickListener(OnDelClickListener delClickListener) {
        this.delClickListener = delClickListener;
    }

    // 添加列
    public void addColumn() {
        // 最多8列
        if (columnCount == 8) {
            Toast.makeText(getContext(), "最多可以设置8列", Toast.LENGTH_SHORT).show();
            return;
        }
        columnCount += 1;
        // 增加一列的时候是给最右边增加的,所以我们要给相对应的位置添加内容,内容为空
        for (int i = 1; i < columnCount * rowCount + 1; i++) {
            if (i % columnCount == 0) {
                contentTxt.add(i - 1, "");
            }
        }
        // 重新计算单元格列宽
        columnWidth = (width - 2 * lineStrokeWidth) / columnCount;
        invalidate();
    }

    /**
     * 列-1
     */
    public void subtractColumn() {
        // 如果列==1的情况下,不允许在减
        if (columnCount == 1) {
            return;
        }
        columnCount -= 1;
        int pos = contentTxt.size();
        if (rowCount != 1) {
            for (int i = pos; i > 0; i--) {// 循环减少最后一列对应的content
                if (i % rowCount == 0) {
                    contentTxt.remove(i - 1);
                }
            }
        } else {// 如果行==1的话,减少最后一列,同时contentTxt也减少最后一个
            contentTxt.remove(pos - 1);
        }
        // 从新计算单元格列宽
        columnWidth = (width - 2 * lineStrokeWidth) / columnCount;
        invalidate();
    }

    /**
     * 增加行
     */
    public void addRow() {
        if (rowCount == 8) {
            Toast.makeText(getContext(), "最多可以设置8行", Toast.LENGTH_SHORT).show();
            return;
        }
        rowCount += 1;
        for (int i = 0; i < columnCount; i++) {
            contentTxt.add("");
        }
        rowHeight = (height - 2 * lineStrokeWidth) / rowCount;
        invalidate();
    }

    /**
     * 减少行
     */
    public void subtractRow() {
        if (rowCount == 1) {
            return;
        }
        rowCount -= 1;
        for (int i = 0; i < columnCount; i++) {
            contentTxt.remove(contentTxt.size() - 1);
        }
        rowHeight = (height - 2 * lineStrokeWidth) / rowCount;
        invalidate();
    }

    // 这是表格的line样式和宽度
    public void setOuterFull(boolean isBolb) {
        if (isBolb) {
            lineStrokeWidth = 5;
        } else {
            lineStrokeWidth = 3;
        }
        paintBorder.setPathEffect(null);
        paintBorder.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setOuterDash(boolean isBolb) {
        if (isBolb) {
            lineStrokeWidth = 5;
        } else {
            lineStrokeWidth = 3;
        }
        paintBorder.setPathEffect(dashPathEffect);
        paintBorder.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setInnerFull(boolean isBolb) {
        if (isBolb) {
            lineStrokeWidth = 5;
        } else {
            lineStrokeWidth = 3;
        }
        paintInsideLine.setPathEffect(null);
        paintInsideLine.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    public void setInnerDash(boolean isBolb) {
        if (isBolb) {
            lineStrokeWidth = 5;
        } else {
            lineStrokeWidth = 3;
        }
        paintInsideLine.setPathEffect(dashPathEffect);
        paintInsideLine.setStrokeWidth(lineStrokeWidth);
        invalidate();
    }

    /**
     * 设置表格line的色值
     *
     * @param color
     */
    public void setOuterColor(int color) {
        paintBorder.setColor(color);
        invalidate();
    }

    public void setInnerColor(int color) {
        paintInsideLine.setColor(color);
        invalidate();
    }

    // 获取x轴上缩放因子,即缩放比例
    private float scaleFactorX(MotionEvent event) {
        return (event.getRawX() - (screenWidth - originalWidth) / 2 - originalCenterX) / originalCenterX;
    }

    // 获取y轴上缩放因子,即缩放比例
    private float scaleFactorY(MotionEvent event) {
        return (event.getRawY() - getTop() - ScreenUtils.getStatusBarHeight(getContext())) / originalCenterY - 1f;
    }

}

