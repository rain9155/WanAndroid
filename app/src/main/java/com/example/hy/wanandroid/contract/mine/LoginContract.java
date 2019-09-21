package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;

/**
 * 登陆界面的Contract
 * Created by 陈健宇 at 2018/11/16
 */
public interface LoginContract {

    interface View extends IView {

        void setAccountErrorView(String error);//设置用户名错误提示
        void setPasswordErrorView(String error);//设置密码错误提示
        void requestFocus(boolean cancel);//输入框获取焦点
        void loginSuccess();//登陆成功

    }

    interface Presenter {
        void login(String account, String password);//登陆
    }

}
