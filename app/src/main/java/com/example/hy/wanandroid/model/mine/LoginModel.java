package com.example.hy.wanandroid.model.mine;

import com.example.hy.wanandroid.contract.mine.LoginContract;
import com.example.hy.wanandroid.core.network.api.MineApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Login;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/11/16
 */
public class LoginModel implements LoginContract.Model {

    private MineApis mMineApis;

    @Inject
    public LoginModel(MineApis mineApis) {
        this.mMineApis = mineApis;
    }

    @Override
    public Observable<BaseResponse<Login>> getLoginRequest(String username, String password) {
        return mMineApis.getLoginRequest(username, password);
    }
}
