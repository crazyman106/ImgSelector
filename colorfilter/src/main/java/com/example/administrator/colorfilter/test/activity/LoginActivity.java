package com.example.administrator.colorfilter.test.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.sqchen.bmobmvptest.test.BaseActivity;
import com.sqchen.bmobmvptest.test.presenter.UserPresenter;
import com.sqchen.bmobmvptest.test.view.ILoginView;


/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public class LoginActivity extends BaseActivity<ILoginView, UserPresenter> implements ILoginView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public String getUserName() {
        return null;
    }

    @Override
    public String getUserPwd() {
        return null;
    }

    @Override
    public void showLoading() {

    }

    private void login() {
    }

    private void register() {
    }

    @Override
    public void dismissLoading() {

    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }
}
