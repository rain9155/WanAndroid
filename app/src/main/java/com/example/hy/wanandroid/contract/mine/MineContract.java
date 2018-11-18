package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Login;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface MineContract {
    interface View extends IView {
        void showLoginView();
        void showLogoutView();
    }

    interface Presenter extends IPresenter<MineContract.View> {
        void logout();//退出登陆
    }

    interface Model{
        Observable<BaseResponse<Login>> getLogoutRequest();
    }
}
