package com.example.administrator.colorfilter.test.presenter;

import java.lang.ref.WeakReference;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public abstract class BasePresenter<V> {
    protected WeakReference<V> mViewRef;

    public void attachView(V view) {
        mViewRef = new WeakReference<V>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

    protected V getView() {
        return mViewRef.get();
    }
}
