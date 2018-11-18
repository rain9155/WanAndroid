package com.example.hy.wanandroid.core.network.api;

import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Login;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * 我的模块所api
 * Created by 陈健宇 at 2018/11/16
 */
public interface MineApis {

    /**
     * 登陆
     * http://www.wanandroid.com/user/login
     */
    @POST("user/login")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> getLoginRequest(
            @Field("username") String userName,//用户名
            @Field("password") String password//密码
    );

    /**
     * 退出登陆
     * http://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    Observable<BaseResponse<Login>> getLogoutRequest();
}
