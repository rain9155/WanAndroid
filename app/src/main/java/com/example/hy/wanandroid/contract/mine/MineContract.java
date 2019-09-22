package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface MineContract {
    interface View extends IView {
        void showLoginView();
        void showLogoutView();
        void changeFaceOrBackground(int flag);
    }

    interface Presenter{
        void logout();//退出登陆
        boolean getNightModeState();
    }

}
