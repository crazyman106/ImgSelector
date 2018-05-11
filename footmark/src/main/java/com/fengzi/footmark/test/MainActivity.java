package com.fengzi.footmark.test;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengzi.footmark.R;

import java.io.File;

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private static final String DEBUG_TAG = "Gestures";
    private GestureDetectorCompat mDetector;
    private ImageView imageView;
    TMEditText editText;
    StyleTextView textView;
    SketchView sketchView;

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDetector = new GestureDetectorCompat(this, this);
        mDetector.setOnDoubleTapListener(this);
        imageView = findViewById(R.id.image);
        Bitmap bitmap = BitmapUtils.mergeBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.online_before_answer_bg), BitmapFactory.decodeResource(getResources(), R.mipmap.icon_logo));
        imageView.setImageBitmap(bitmap);


        String path = "aa/bb/t.txt";
        if (path.contains(File.separator)) {
            int i = path.lastIndexOf(File.separator);
            String substring = path.substring(i + 1);
            Log.e("PATH", substring);

        } else {
            Log.e("PATH", "false");
        }
        tv = findViewById(R.id.txt);
        tv.setTypeface(FontUtils.getTypeFace(this,"a.TTF"));

        editText = findViewById(R.id.edittext_);
        editText.startAnimation();


        textView = findViewById(R.id.text);
        textView.setText(getResources().getString(R.string.text));
        textView.setTextColor(Color.RED);
        textView.setBgColor(Color.WHITE);

        sketchView = findViewById(R.id.imageview);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(DEBUG_TAG, "onSingleTapConfirmed: " + e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(DEBUG_TAG, "onDoubleTap: " + e.toString());
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(DEBUG_TAG, "onDoubleTapEvent: " + e.toString());
        return true;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(DEBUG_TAG, "onDown: " + e.toString());
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onShowPress: " + e.toString());
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.d(DEBUG_TAG, "onSingleTapUp: " + e.toString());
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.d(DEBUG_TAG, "onScroll: " + e1.toString() + e2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onLongPress: " + e.toString());
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + e1.toString() + e2.toString());
        return true;
    }
}
