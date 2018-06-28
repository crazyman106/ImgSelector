package com.example.administrator.colorfilter.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sqchen.bmobmvptest.test.presenter.BasePresenter;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public abstract class BaseActivity<V, P extends BasePresenter<V>> extends AppCompatActivity {

    private P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = createPresenter();
        mPresenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除关联
        mPresenter.detachView();
    }

    protected abstract P createPresenter();
}
