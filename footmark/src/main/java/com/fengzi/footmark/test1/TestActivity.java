package com.fengzi.footmark.test1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fengzi.footmark.R;
import com.fengzi.footmark.test.StyleTextView;

/**
 * @author lijunjie on 2018/5/11 0011.
 * @description
 */

public class TestActivity extends Activity {

    StyleTextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        textView = findViewById(R.id.text);
        textView.setText("我是中国人");
    }
}
