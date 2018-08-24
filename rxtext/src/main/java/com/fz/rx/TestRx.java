package com.fz.rx;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;

/**
 * @author lijunjie on 2018/8/23 0023.
 * @description
 */

public class TestRx {
    public void test() {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                }
            }
        });
        Subscription subscription = observable.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
            }
        });

    }
}
