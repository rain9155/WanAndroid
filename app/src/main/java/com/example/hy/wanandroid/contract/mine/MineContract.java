package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Login;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface MineContract {
    interface View extends BaseView {
        void showLoginView();
        void showLogoutView();
        void changeFaceOrBackground(boolean isChangeFace);
    }

    interface Presenter extends IPresenter<MineContract.View> {
        void logout();//退出登陆
    }

}
