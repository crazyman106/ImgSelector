package com.example.administrator.colorfilter.test.bean;


/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public class User {
    private String userPwd;
    private String userName;

    public User(String userPwd, String userName) {
        this.userPwd = userPwd;
        this.userName = userName;
    }

    public User(String tableName, String userPwd, String userName) {
        this.userPwd = userPwd;
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
