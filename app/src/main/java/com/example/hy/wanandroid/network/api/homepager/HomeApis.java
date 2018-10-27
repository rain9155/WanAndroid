package com.example.hy.wanandroid.network.api.homepager;

import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 首页所有api
 * Created by 陈健宇 at 2018/10/25
 */
public interface HomeApis {

    /***
     * 首页banner
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerDatas();

}
