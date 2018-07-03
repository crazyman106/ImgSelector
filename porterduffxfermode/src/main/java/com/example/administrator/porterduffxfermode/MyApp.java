package com.example.administrator.porterduffxfermode;

import android.app.Application;

import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by Administrator on 2018/7/3.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化 x5内核
        new Thread(new Runnable() {
            @Override
            public void run() {
                QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {
                    @Override
                    public void onCoreInitFinished() {

                    }
                    @Override
                    public void onViewInitFinished(boolean b) {

                    }
                };
                QbSdk.initX5Environment(getApplicationContext(),cb);

            }
        }).start();
    }
}
