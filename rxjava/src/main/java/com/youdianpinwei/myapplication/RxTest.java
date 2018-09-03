package com.youdianpinwei.myapplication;

import org.jetbrains.annotations.TestOnly;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author lijunjie on 2018/8/30 0030.
 * @description
 */

public class RxTest {

    public void test() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("hahahha");
            }
        }).map(new Function<String, PhontBean>() {

            @Override
            public PhontBean apply(String t) throws Exception {
                return new PhontBean(t);
            }
        })
                .doOnNext(new Consumer<PhontBean>() {

                    @Override
                    public void accept(PhontBean t) throws Exception {
                        System.out.println("转换成功:" + t.getName());

                    }
                })
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<PhontBean>() {

                    @Override
                    public void accept(PhontBean t) throws Exception {
                        System.out.println("成功:" + t.getName());
                    }
                }, new Consumer<Throwable>() {

                    @Override
                    public void accept(Throwable t) throws Exception {
                        System.out.println("失败:" + t.getMessage());
                    }
                });
    }
}
