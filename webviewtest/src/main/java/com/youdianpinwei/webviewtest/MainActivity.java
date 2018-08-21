package com.youdianpinwei.webviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webview);

        webView.loadUrl("https://yujing.youdianpinwei.com/appintelligentguide/maps?scenicid=f61e4f62-8b97-47b9-a874-3d6a14c2da7e&token=Gt4567P9SQLhA9yud5wd3FLWUiOKKG7CAGmUobVBZiJ4567iydNn9uumS69Vr4567azhHXEnqV26oNZJKavAaamKapmIw441CoUFGUDqQFpsC20123mw0uHOZnbDxS0hSEr7J24mSj7b");
    }
}
