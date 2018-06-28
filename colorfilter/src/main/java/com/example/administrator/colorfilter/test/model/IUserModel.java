package com.example.administrator.colorfilter.test.model;

import com.example.administrator.colorfilter.test.ICallBack;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public interface IUserModel {

    void login(String userName, String userPwd, ICallBack callback);

    void register(String userName, String userPwd, ICallBack callback);


}
