package com.fengzi.footmark.test;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fengzi.footmark.R;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @author lijunjie on 2018/5/9 0009.
 * @description
 */

public class TMEditText extends RelativeLayout {
    private ImageView translate;
    ScheduledExecutorService executor;

    public TMEditText(Context context) {
        this(context, null);
    }

    public TMEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TMEditText(final Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.tm_edittext_layout, this, true);
        translate = view.findViewById(R.id.tm_edittext_transformation);
        executor = Executors.newScheduledThreadPool(5);
    }

    float offset = 0.1f;
    float cur_size = 1f;

    public void startAnimation() {
//        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "rotation", 0, 90);
//        animator.setInterpolator(new LinearInterpolator());
//        animator.setDuration(800);
//        animator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("ontouchevent",event.getAction()+"");
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            this.getMatrix().postScale(cur_size,cur_size,getWidth()/2,getHeight()/2);
            cur_size+= offset;
            Log.e("ontouchevent",cur_size+"");
        }
        invalidate();
        return true;
    }
}
