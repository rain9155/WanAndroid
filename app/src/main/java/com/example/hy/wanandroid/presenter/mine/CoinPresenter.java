package com.example.hy.wanandroid.presenter.mine;

import com.example.commonlib.utils.LogUtil;
import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.contract.mine.CoinContract;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.entity.Coins;
import com.example.hy.wanandroid.entity.UserCoin;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

public class CoinPresenter extends BaseActivityPresenter<CoinContract.View> implements CoinContract.Presenter {

    @Inject
    public CoinPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribeEvent() {
        super.subscribeEvent();
        addSubscriber(
                RxBus.getInstance().toObservable(TokenExpiresEvent.class)
                .subscribe(tokenExpiresEvent -> mView.reLoad())
        );
    }

    @Override
    public void getUserCoin() {
        addSubscriber(
                mModel.getUserCoin()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new DefaultObserver<UserCoin>(mView, false, false){
                    @Override
                    public void onNext(UserCoin userCoin) {
                        super.onNext(userCoin);
                        mView.showUserCoin(userCoin);
                    }
                })
        );
    }

    @Override
    public void getCoins(int pageNum) {
        addSubscriber(
                mModel.getCoins(pageNum)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleResult())
                        .subscribeWith(new DefaultObserver<Coins>(mView){
                            @Override
                            public void onNext(Coins coins) {
                                super.onNext(coins);
                                mView.showCoins(coins.getDatas(), coins.isOver());
                            }

                        })
        );
    }
}