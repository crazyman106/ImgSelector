package com.example.administrator.porterduffxfermode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;

import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String url = "http://weixin.youdianpinwei.com/dist/album.html?usermenoryid=0F56760C-E398-4C53-AB94-A8978B44F9ED&isPreview=1";
    private ProgressWebView webView;
    private WebSettings webSettings;
    private WebChromeClient webChromeClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 网页中的视频，上屏幕的时候，可能出现闪烁的情况，需要如下设置
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        //避免输入法界面弹出后遮挡输入光标的问题
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        MyWeb(url);
    }

    //WebView
    private void MyWeb(String url) {
        webView = (ProgressWebView) findViewById(R.id.webview);
        webView.setDrawingCacheEnabled(true);
        webChromeClient = new WebChromeClient();
        webView.setWebChromeClient(webChromeClient);
        webSettings = webView.getSettings();

        // 修改ua使得web端正确判断(加标识+++++++++++++++++++++++++++++++++++++++++++++++++++++)
//        String ua = webSettings.getUserAgentString();
//        webSettings.setUserAgentString(ua + "这里是增加的标识");

        // 网页内容的宽度是否可大于WebView控件的宽度
//        webSettings.setLoadWithOverviewMode(false);
        // 保存表单数据
        webSettings.setSaveFormData(true);

        webSettings.setLoadWithOverviewMode(true);
        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        // 是否应该支持使用其屏幕缩放控件和手势缩放
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        //隐藏原生的缩放控件
        webSettings.setDisplayZoomControls(false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.requestFocus(); //此句可使html表单可以接收键盘输入
        webView.setFocusable(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        // 启动应用缓存
        webSettings.setAppCacheEnabled(false);
        // 设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        // 设置此属性，可任意比例缩放。
        webSettings.setUseWideViewPort(true);
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        //  页面加载好以后，再放开图片
        webSettings.setBlockNetworkImage(false);
        // 使用localStorage则必须打开
        webSettings.setDomStorageEnabled(true);
        // 排版适应屏幕
        webSettings.setLayoutAlgorithm(com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        webSettings.setSupportMultipleWindows(true);
        webSettings.setUseWideViewPort(true); // 关键点
        webSettings.setAllowFileAccess(true); // 允许访问文件
        //将图片调整到适合webview的大小
        webSettings.setUseWideViewPort(true);
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);
        //其他细节操作
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        webSettings.setDomStorageEnabled(true);//JS在HTML里面设置了本地存储localStorage，java中使用localStorage则必须打开
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUseWideViewPort(true); //自适应屏幕

        //以下接口禁止(直接或反射)调用，避免视频画面无法显示：
        //webView.setLayerType();
        webView.setDrawingCacheEnabled(true);

        //去除QQ浏览器推广广告
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                if (outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public com.tencent.smtt.export.external.interfaces.WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                FileInputStream input;
                String url = webResourceRequest.getUrl().toString();
                Log.e("url", url);
                String key = "http://androidimg";
                /*如果请求包含约定的字段 说明是要拿本地的图片*/
                if (url.contains(".jpg") || url.contains(".mp3")) {
                    String imgPath = url.replace(key, "");
                    try {
                    /*重新构造WebResourceResponse  将数据已流的方式传入*/
                        String micUlr = "/storage/emulated/0/ypw/intelligent_scenic/f61e4f62-8b97-47b9-a874-3d6a14c2da7e#/8369c288-5e25-4eb3-b420-5fcc37f7be34.jpg";
                        Log.e("micUlr", micUlr);
                        input = new FileInputStream(new File(micUlr));
                        com.tencent.smtt.export.external.interfaces.WebResourceResponse response = new com.tencent.smtt.export.external.interfaces.WebResourceResponse(MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(micUlr)), "UTF-8", input);
                        return response;
                    } catch (Exception e) {
                        Log.e("exception", e.getMessage().toString());
                    }
                }
                return super.shouldInterceptRequest(webView, webResourceRequest);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {


                webView.loadUrl(s);
                return true;
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);

            }

            @Override
            public void onReceivedError(WebView webView, int i, String s, String s1) {
                super.onReceivedError(webView, i, s, s1);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            // 申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            Log.e("loadurl", "loadurl");
            webView.loadUrl("http://yujing.youdianpinwei.com/IntelligentGuide/Maps?ScenicID=F61E4F62-8B97-47B9-A874-3D6A14C2DA7E&FSourceScenicId=53c47abb-10d5-4d7d-837d-3918257b5f0a&FSocreScenicAccount=10615773-50c2-40bf-8bbc-0060618ffe34");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 获得授权
                webView.loadUrl("http://yujing.youdianpinwei.com/IntelligentGuide/Maps?ScenicID=F61E4F62-8B97-47B9-A874-3D6A14C2DA7E&FSourceScenicId=53c47abb-10d5-4d7d-837d-3918257b5f0a&FSocreScenicAccount=10615773-50c2-40bf-8bbc-0060618ffe34");
            } else {
            }
        }
    }
}
