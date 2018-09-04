package com.example.administrator.paint;

import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    CustomTextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.txt);
        Paint.FontMetrics mFontMetrics = tv.getPaint().getFontMetrics();
        Log.e("FontMetricxView", "ascent：" + mFontMetrics.ascent);
        Log.e("FontMetricxView", "top：" + mFontMetrics.top);
        Log.e("FontMetricxView", "leading：" + mFontMetrics.leading);
        Log.e("FontMetricxView", "descent：" + mFontMetrics.descent);
        Log.e("FontMetricxView", "bottom：" + mFontMetrics.bottom);
        Log.e("FontMetricxView", "baseline：" + tv.getBaseline());
//        tv.setText("我是中国人");
        tv.setTextSize(50);
        Paint.FontMetrics mFontMetrics1 = tv.getPaint().getFontMetrics();
        Log.e("FontMetricxView", "ascent：" + mFontMetrics1.ascent);
        Log.e("FontMetricxView", "top：" + mFontMetrics1.top);
        Log.e("FontMetricxView", "leading：" + mFontMetrics1.leading);
        Log.e("FontMetricxView", "descent：" + mFontMetrics1.descent);
        Log.e("FontMetricxView", "bottom：" + mFontMetrics1.bottom);
        Log.e("FontMetricxView", "baseline：" + tv.getBaseline());
        String str = "李  俊  杰";
        tv.setText(str);
        Log.e("mainActivity", tv.getText().toString().length() + "-" + str.length());
        textView = findViewById(R.id.text);
        textView.setText("我是");
    }


    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("text_width2", tv.getMeasuredWidth() + "-");
            }
        }, 1000);
    }
}
