package com.example.cusexcel.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.example.cusexcel.R;

/**
 * @author lijunjie on 2018/9/3 0003.
 * @description
 */

public class TableLayout extends RelativeLayout {
    public TableLayout(Context context) {
        this(context, null);
    }

    TableView tableView;

    public TableLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        tableView = new TableView(context);
        addView(tableView);
        RelativeLayout.LayoutParams layoutParams = (LayoutParams) tableView.getLayoutParams();
        layoutParams.height = Util.dip2px(context, 200f);
        layoutParams.width = Util.dip2px(context, 360f);
        tableView.setLayoutParams(layoutParams);

        tableView.setOnDoubleClickListener(new TableView.OnDoubleClickListener() {
            @Override
            public void onDoubleClick(int index) {

            }
        });
    }

    float scale = 1f;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            scale += 0.01;
/*            setScaleX(scale);
            setScaleY(scale);*/
            Log.e("width_height", getWidth() + "--" + getHeight());
            RelativeLayout.LayoutParams layoutParams = (LayoutParams) tableView.getLayoutParams();
            layoutParams.height = Util.dip2px(getContext(), 200f * scale);
            layoutParams.width = Util.dip2px(getContext(), 360f * scale);
            tableView.setLayoutParams(layoutParams);
            postDelayed(this, 200);
        }
    };

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }
}
