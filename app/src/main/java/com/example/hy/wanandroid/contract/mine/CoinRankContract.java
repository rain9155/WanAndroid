package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.entity.UserCoin;

import java.util.List;

public interface CoinRankContract {

    interface View extends IView {
        void showUserRank(UserCoin userCoin);
        void showCoinRank(List<UserCoin> coinRanks, boolean isOver);
    }

    interface Presenter {
        void getUserRank();
        void getCoinRanks(int pageNum);
    }

}