package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Login;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * 我的界面的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class MinePresenter extends BasePresenter<MineContract.View> implements MineContract.Presenter{

    @Inject
    public MinePresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribleEvent() {
        super.subscribleEvent();
        addSubcriber(
                RxBus.getInstance().toObservable(StatusBarEvent.class)
                        .compose(RxUtils.switchSchedulers())
                        .subscribe(statusBarEvent -> mView.setStatusBarColor(statusBarEvent.isSet()))
        );

        addSubcriber(
                RxBus.getInstance().toObservable(LoginEvent.class)
                        .filter(loginEvent -> loginEvent.isLogin())
                        .subscribe(loginEvent -> mView.showLoginView())
        );

//        addSubcriber(
//                RxBus.getInstance().toObservable(LoginEvent.class)
//                .filter(loginEvent -> !loginEvent.isLogin())
//                .subscribe(loginEvent -> mView.showLogoutView())
//        );

        addSubcriber(
                RxBus.getInstance().toObservable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(loginEvent -> logout())
        );

    }

    @Override
    public void logout() {
        addSubcriber(
                mModel.getLogoutRequest()
                .compose(RxUtils.switchSchedulers())
                .subscribeWith(new DefaultObserver<BaseResponse<Login>>(mView, false, false){
                    @Override
                    public void onNext(BaseResponse<Login> baseResponse) {
                        super.onNext(baseResponse);
                        if (baseResponse.getData() != null) return;
                        User.getInstance().reset();
                        mView.showLogoutView();
                    }
                })
        );
    }
}
