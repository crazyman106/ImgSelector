package com.example.administrator.paint.funny.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.administrator.paint.R;
import com.example.administrator.paint.funny.util.BitmapUtils;
import com.example.administrator.paint.funny.util.LogUtils;

/**
 * Created by yue on 2016/4/22.
 */
public class AddWordFrame extends FrameLayout {
    private Paint paint;
    private int mImageHeight = 50, mImageWidth = 200;

    private Bitmap bitmap;

    private Context context;
    private AddWordOutsideLinearLayout layout;

    private float sx;
    private float sy;
    private float dx;
    private float dy;
    private float degrees;
    private float px;
    private float py;
    private float centerX;
    private float centerY;

    public PointF leftTop = new PointF();
    public PointF rightTop = new PointF();
    public PointF leftBottom = new PointF();
    public PointF rightBottom = new PointF();
    private boolean isSelect = false;
    public Bitmap bitDelete = null;
    public Bitmap bitMove = null;
    public Bitmap bitEdit = null;

    Matrix matrix = new Matrix();

    private boolean isHeng = true;
    private String allText;
    private int xiao;
    private boolean isShowDelete = true;

    public AddWordFrame(Context context) {
        super(context);
        this.context = context;
        initPaint();
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordFrame(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initPaint();
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordFrame(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initPaint();
        addWordView();
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setWillNotDraw(false);
    }

    public AddWordOutsideLinearLayout getLayout() {
        return layout;
    }

    public String getAllText() {
        return allText;
    }

    public void setAllText(String allText) {
        this.allText = allText;
    }

    private void addWordView() {
        layout = new AddWordOutsideLinearLayout(context);
        layout.setTextColor(context.getResources().getColor(R.color.colorAccent));
        layout.setTextSize(15);
        layout.setTextViewOrientation(LinearLayout.VERTICAL);
        layout.setText("请输入文字");

        bitmap = BitmapUtils.convertViewToBitmap(layout);

        mImageWidth = bitmap.getWidth();
        mImageHeight = bitmap.getHeight();

        LogUtils.i("mImageWidth===" + mImageWidth);
        LogUtils.i("mImageHeight===" + mImageHeight);

        layout.setmImageWidth(mImageWidth);
        layout.setmImageHeight(mImageHeight);

        layout.setSelect(true);
        addView(layout);
    }

    public int getmImageHeight() {
        return mImageHeight;
    }

    public void setmImageHeight(int mImageHeight) {
        this.mImageHeight = mImageHeight;
    }

    public int getmImageWidth() {
        return mImageWidth;
    }

    public void setmImageWidth(int mImageWidth) {
        this.mImageWidth = mImageWidth;
    }

    public void postScale(float sx, float sy, float centerX, float centerY) {
        this.sx = sx;
        this.sy = sy;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    public void postTranslate(float dx, float dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public void postRotate(float degrees, float px, float py) {
        this.degrees = degrees;
        this.px = px;
        this.py = py;
    }

    public boolean isHeng() {
        return isHeng;
    }

    public void setHeng(boolean heng) {
        isHeng = heng;
        invalidate();
    }

    private void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(context.getResources().getColor(R.color.colorAccent));
        paint.setStrokeWidth(3f);
        if (bitDelete == null) {
            bitDelete = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.btn_sticker_cancel_n);
        }
        if (bitMove == null) {
            bitMove = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.btn_sticker_turn_n);
        }
        if (bitEdit == null) {
            bitEdit = BitmapFactory.decodeResource(getResources(),
                    R.mipmap.btn_edit);
        }
        xiao = bitDelete.getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setVisibility(INVISIBLE);
        if (isSelect) {
            canvas.drawLine(leftTop.x, leftTop.y, rightTop.x, rightTop.y, paint);
            canvas.drawLine(leftBottom.x, leftBottom.y, rightBottom.x, rightBottom.y, paint);
            canvas.drawLine(leftTop.x, leftTop.y, leftBottom.x, leftBottom.y, paint);
            canvas.drawLine(rightTop.x, rightTop.y, rightBottom.x, rightBottom.y, paint);
            if (isShowDelete) {
                canvas.drawBitmap(bitDelete, leftTop.x - xiao, leftTop.y - xiao, paint);
            }
            canvas.drawBitmap(bitMove, rightBottom.x - xiao, rightBottom.y - xiao, paint);
            canvas.drawBitmap(bitEdit, rightTop.x - xiao, rightTop.y - xiao, paint);
        }

        this.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        canvas.concat(matrix);
        this.setLayerType(View.LAYER_TYPE_NONE, null);
        setVisibility(VISIBLE);
    }

    @Override
    public Matrix getMatrix() {
        return matrix;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select, boolean isShowDelete) {
        this.isShowDelete = isShowDelete;
        this.isSelect = select;
    }

    public void setMatrix(Matrix matrix) {
        this.matrix = matrix;
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                postInvalidate();
            }
        });
    }

}
