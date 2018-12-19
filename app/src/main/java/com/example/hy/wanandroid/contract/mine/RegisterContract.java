package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Login;

import io.reactivex.Observable;

/**
 * 注册的Contract
 * Created by 陈健宇 at 2018/11/19
 */
public interface RegisterContract {

    interface View extends BaseView {

        void setAccountErrorView(String error);//设置用户名错误提示
        void setPasswordErrorView(String error);//设置密码错误提示
        void setRePasswordErrorView(String error);//设置再次输入密码错误提示
        void requestFocus(boolean cancel);//输入框获取焦点
        void registerSuccess();//注册成功
    }

    interface Presenter extends IPresenter<View>{
        void register(String username, String password, String rePassword);//注册
    }

}
