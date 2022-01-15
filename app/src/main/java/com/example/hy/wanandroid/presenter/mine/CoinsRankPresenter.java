package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.contract.mine.CoinRankContract;
import com.example.hy.wanandroid.entity.CoinRanks;
import com.example.hy.wanandroid.entity.UserCoin;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

public class CoinsRankPresenter extends BaseActivityPresenter<CoinRankContract.View> implements CoinRankContract.Presenter {

    @Inject
    public CoinsRankPresenter(DataModel dataModel) {
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
    public void getUserRank() {
        addSubscriber(
                mModel.getUserCoin()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new DefaultObserver<UserCoin>(mView, false, false){
                    @Override
                    public void onNext(UserCoin userCoin) {
                        super.onNext(userCoin);
                        mView.showUserRank(userCoin);
                    }
                })
        );
    }

    @Override
    public void getCoinRanks(int pageNum) {
        addSubscriber(
                mModel.getCoinRanks(pageNum)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleResult())
                .subscribeWith(new DefaultObserver<CoinRanks>(mView){
                    @Override
                    public void onNext(CoinRanks coinRanks) {
                        super.onNext(coinRanks);
                        mView.showCoinRank(coinRanks.getDatas(), coinRanks.isOver());
                    }
                })
        );
    }

}