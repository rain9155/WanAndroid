package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BaseFragmentPresenter;
import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.event.ChangeFaceEvent;
import com.example.hy.wanandroid.event.NightModeEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.Login;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

/**
 * 我的界面的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class MinePresenter extends BaseFragmentPresenter<MineContract.View> implements MineContract.Presenter{

    @Inject
    public MinePresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribeEvent() {
        super.subscribeEvent();

        addSubscriber(
                RxBus.getInstance().toObservable(LoginEvent.class)
                        .filter(LoginEvent::isLogin)
                        .subscribe(loginEvent -> mView.showLoginView())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(LoginEvent.class)
                .filter(loginEvent -> !loginEvent.isLogin())
                .subscribe(loginEvent -> logout())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(NightModeEvent.class)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<NightModeEvent>(mView, false, false){
                            @Override
                            public void onNext(NightModeEvent nightModeEvent) {
                                mView.useNightNode(nightModeEvent.isNight());
                            }

                            @Override
                            protected void unknown() {
                                mView.showToast(App.getContext().getString(R.string.error_switch_fail));
                            }
                        }));

        addSubscriber(
                RxBus.getInstance().toObservable(ChangeFaceEvent.class)
                .subscribe(changeFaceEvent -> mView.changeFaceOrBackground(changeFaceEvent.isChangeFace()))
        );
    }

    @Override
    public void logout() {
        addSubscriber(
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

    @Override
    public boolean getNightModeState() {
        return mModel.getNightModeState();
    }
}
