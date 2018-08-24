package com.fz.rx;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * @author lijunjie on 2018/8/23 0023.
 * @description
 */
public class TestRxTest {
    @Test
    public void test1() throws Exception {
        Observable<Integer> observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 5; i++) {
                    subscriber.onNext(i);
                    System.out.println(Thread.currentThread().getName()+"-");
                }
            }
        });
        Subscription subscription = observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                System.out.println(Thread.currentThread().getName()+"-");
                System.out.println(integer);
            }
        });
        subscription.unsubscribe();
    }

}