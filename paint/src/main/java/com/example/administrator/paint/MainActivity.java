package com.example.administrator.paint;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.txt);
        tv.setText("ac");
        Paint.FontMetrics mFontMetrics = tv.getPaint().getFontMetrics();
        Log.e("FontMetricxView", "ascent：" + mFontMetrics.ascent);
        Log.e("FontMetricxView", "top：" + mFontMetrics.top);
        Log.e("FontMetricxView", "leading：" + mFontMetrics.leading);
        Log.e("FontMetricxView", "descent：" + mFontMetrics.descent);
        Log.e("FontMetricxView", "bottom：" + mFontMetrics.bottom);
        Log.e("FontMetricxView", "baseline：" + tv.getBaseline());
    }
}
