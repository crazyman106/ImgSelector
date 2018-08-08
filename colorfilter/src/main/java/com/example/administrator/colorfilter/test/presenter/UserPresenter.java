package com.example.administrator.colorfilter.test.presenter;


import com.example.administrator.colorfilter.test.ICallBack;
import com.example.administrator.colorfilter.test.model.IUserModel;
import com.example.administrator.colorfilter.test.model.IuserModelImpl;
import com.example.administrator.colorfilter.test.view.ILoginView;

/**
 * @author lijunjie on 2018/6/28 0028.
 * @description
 */

public class UserPresenter extends BasePresenter<ILoginView> {

    IUserModel iUserModel;
    ILoginView iLoginView;

    public UserPresenter(ILoginView iLoginView) {
        this.iUserModel = new IuserModelImpl();
        this.iLoginView = iLoginView;
    }

    public void login(String name, String pwd) {
        iLoginView.showLoading();
        if (iUserModel != null) {
            iUserModel.login(name, pwd, new ICallBack() {
                @Override
                public void onSuccess(String message) {
                    iLoginView.dismissLoading();
//                    Toast.makeText(getApplicationContext(),
//                            "登录成功！",
//                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(int erCode, String erMessage) {
                    iLoginView.dismissLoading();
//                    Toast.makeText(getApplicationContext(),
//                            "登录失败！",
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void register(String name, String pwd) {
        iLoginView.showLoading();
        if (iUserModel != null) {
            iUserModel.login(name, pwd, new ICallBack() {
                @Override
                public void onSuccess(String message) {
                    iLoginView.dismissLoading();
//                    Toast.makeText(getApplicationContext(),
//                            "登录成功！",
//                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailed(int erCode, String erMessage) {
                    iLoginView.dismissLoading();
//                    Toast.makeText(getApplicationContext(),
//                            "登录失败！",
//                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
