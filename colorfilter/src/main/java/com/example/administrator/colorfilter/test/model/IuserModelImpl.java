package com.example.administrator.colorfilter.test.model;


import com.example.administrator.colorfilter.test.ICallBack;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public class IuserModelImpl implements IUserModel {
    @Override
    public void login(String userName, String userPwd, final ICallBack callback) {
       /* Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 调用登录
                // 登录成功
                subscriber.onNext("login is success");
                // 登录失败
                subscriber.onError(new Throwable("login is failed"));
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailed(throwable.hashCode(), throwable.getMessage().toString());
                    }

                    @Override
                    public void onNext(String s) {
                        callback.onSuccess(s);
                    }
                });*/
    }

    @Override
    public void register(String userName, String userPwd, final ICallBack callback) {
        /*Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                // 调用注册
                // 注册成功
                subscriber.onNext("register is success");
                // 注册失败
                subscriber.onError(new Throwable("register is failed"));
            }
        }).subscribeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        callback.onFailed(throwable.hashCode(), throwable.getMessage().toString());
                    }

                    @Override
                    public void onNext(String s) {
                        callback.onSuccess(s);
                    }
                });
*/
    }
}
