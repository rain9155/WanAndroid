package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * 登陆界面的Contract
 * Created by 陈健宇 at 2018/11/16
 */
public interface LoginContract {

    interface View extends BaseView {

        void setAccountErrorView(String error);//设置用户名错误提示
        void setPasswordErrorView(String error);//设置密码错误提示
        void requestFocus(boolean cancel);//输入框获取焦点
        void loginSuccess();//登陆成功

    }

    interface Presenter extends BasePresenter<View> {
        void login(String account, String password);//登陆
    }

}
