package com.example.hy.wanandroid.event;

/**
 * 登陆事件
 * Created by 陈健宇 at 2018/11/18
 */
public class LoginEvent {

    private boolean isLogin;

    public LoginEvent(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public boolean isLogin() {
        return isLogin;
    }
}
