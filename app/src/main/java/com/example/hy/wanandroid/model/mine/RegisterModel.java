package com.example.hy.wanandroid.model.mine;

import com.example.hy.wanandroid.contract.mine.RegisterContract;
import com.example.hy.wanandroid.core.network.api.MineApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Login;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Register的Model
 * Created by 陈健宇 at 2018/11/19
 */
public class RegisterModel implements RegisterContract.Model {

    private MineApis mMineApis;

    @Inject
    public RegisterModel(MineApis mineApis) {
        mMineApis = mineApis;
    }

    @Override
    public Observable<BaseResponse<Login>> getRegisterRequest(String username, String password, String rePassword) {
        return mMineApis.getRegisterRequest(username, password, rePassword);
    }
}
