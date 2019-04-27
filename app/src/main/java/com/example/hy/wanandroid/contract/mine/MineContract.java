package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface MineContract {
    interface View extends BaseView {
        void showLoginView();
        void showLogoutView();
        void changeFaceOrBackground(int flag);
    }

    interface Presenter extends BasePresenter<View> {
        void logout();//退出登陆
    }

}
