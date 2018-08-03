package com.youdianpinwei.webviewtest;

import android.app.Application;

import ren.yale.android.cachewebviewlib.WebViewCacheInterceptor;
import ren.yale.android.cachewebviewlib.WebViewCacheInterceptorInst;

/**
 * @author lijunjie on 2018/7/30 0030.
 * @description
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        WebViewCacheInterceptorInst.getInstance().
                init(new WebViewCacheInterceptor.Builder(this));

    }
}
