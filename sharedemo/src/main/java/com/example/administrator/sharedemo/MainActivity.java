package com.example.administrator.sharedemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    MyView myCustomView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        myCustomView = findViewById(R.id.custom);
        Log.i("chuan", "onCreate init myCustomView  width=" + myCustomView.getWidth());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("chuan", "Main onResume");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Log.i("chuan", "onResume Handler post runnable button width=" + myCustomView.getWidth());
            }
        });
        myCustomView.post(new Runnable() {
            @Override
            public void run() {
                Log.i("chuan", "onResume myCustomView post runnable button width=" + myCustomView.getWidth());
            }
        });
    }
}
