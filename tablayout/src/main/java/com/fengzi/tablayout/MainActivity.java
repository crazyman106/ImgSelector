package com.fengzi.tablayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String url = "https://www.cnblogs.com/zhaoyanjun/p/4600663.html";
    WebView webView;
    TextView richText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        settingWebView();

        richText = findViewById(R.id.rich_text);
        setRichText();
    }

    private void setRichText() {
        Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.tip);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        SpannableString spannableString = new SpannableString("pics");
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        richText.append(spannableString);
        richText.append(" 黄花梨,枣木,金丝楠木,阴沉木,柏木,槐木,小叶檀");
    }

    @SuppressLint("JavascriptInterface")
    @JavascriptInterface
    private void settingWebView() {
        WebChromeClient wvcc = new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("web_title", title + "-");
            }
        };

        final WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(wvcc);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.e("web_title_1", webView.getTitle() + "-");
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.e("web_title_2", webView.getTitle() + "-");
                super.onPageFinished(view, url);
            }
        });
        webView.addJavascriptInterface(new JsInterface(MainActivity.this), "android");
    }

    class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public void postMessage(String p) {
            Toast.makeText(mContext, p, Toast.LENGTH_LONG).show();
            Log.e("url", p.toString());
        }

    }

}
