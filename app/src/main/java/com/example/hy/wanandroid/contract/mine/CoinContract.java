package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.entity.UserCoin;

import java.util.List;

public interface CoinContract {

    interface View extends IView {
        void showUserCoin(UserCoin userCoin);
        void showCoins(List<Coin> coinList, boolean over);
        void showCoinsFail();

    }

    interface Presenter{
         void getUserCoin();
         void getCoins(int pageNum);
    }

}