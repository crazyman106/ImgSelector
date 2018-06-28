package com.example.administrator.colorfilter.test;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public interface ICallBack {

    void onSuccess(String message);

    void onFailed(int erCode, String erMessage);
}
