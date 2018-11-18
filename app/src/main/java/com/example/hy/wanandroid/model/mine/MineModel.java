package com.example.hy.wanandroid.model.mine;

import com.example.hy.wanandroid.contract.mine.MineContract;
import com.example.hy.wanandroid.core.network.api.MineApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Login;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 我的界面的Model
 * Created by 陈健宇 at 2018/11/18
 */
public class MineModel implements MineContract.Model {

    private MineApis mMineApis;

    @Inject
    public MineModel(MineApis mineApis) {
        mMineApis = mineApis;
    }

    @Override
    public Observable<BaseResponse<Login>> getLogoutRequest() {
        return mMineApis.getLogoutRequest();
    }
}
