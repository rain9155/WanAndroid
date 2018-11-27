package com.example.hy.wanandroid.model.network.api;

import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.mine.Collection;
import com.example.hy.wanandroid.model.network.entity.mine.CollectionRequest;
import com.example.hy.wanandroid.model.network.entity.mine.Login;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    /**
     * 注册
     * http://www.wanandroid.com/user/register
     */
    @POST("user/register")
    @FormUrlEncoded
    Observable<BaseResponse<Login>> getRegisterRequest(@Field("username") String username,//用户名
                                                       @Field("password") String password,//输入密码
                                                       @Field("repassword") String rePassword//再次输入密码
    );

    /**
     * 获得收藏结果
     * http://www.wanandroid.com/lg/collect/list/0/json
     */
    @GET("lg/collect/list/{pageNum}/json")
    Observable<BaseResponse<CollectionRequest>> getCollectionRequest(@Path("pageNum") int pageNum);

    /**
     * 获得取消收藏结果
     * http://www.wanandroid.com/lg/uncollect/2805/json
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    @FormUrlEncoded
    Observable<BaseResponse<Collection>> getUnCollectionRequest(@Path("id") int id,//收藏在我的收藏列表的id
                                                                @Field("originId") int originId//收藏在原始文章列表的id
    );
}
