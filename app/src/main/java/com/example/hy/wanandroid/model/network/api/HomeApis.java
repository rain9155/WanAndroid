package com.example.hy.wanandroid.model.network.api;

import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.homepager.Articles;
import com.example.hy.wanandroid.model.network.entity.homepager.BannerData;
import com.example.hy.wanandroid.model.network.entity.mine.Collection;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 首页所有api
 * Created by 陈健宇 at 2018/10/25
 */
public interface HomeApis {

    /**
     * 首页banner
     * http://www.wanandroid.com/banner/json
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerData>>> getBannerDatas();

    /**
     * 首页文章列表
     * http://www.wanandroid.com/article/list/1/json
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<Articles>> getArticles(@Path("pageNum") int pageNum);

    /**
     * 收藏文章
     * http://www.wanandroid.com/lg/collect/7562/json
     */
    @POST("lg/collect/{id}/json")
    Observable<BaseResponse<Collection>> getCollectRequest(@Path("id") int id);

    /**
     * 取消收藏
     * http://www.wanandroid.com/lg/uncollect_originId/2333/json
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<BaseResponse<Collection>> getUnCollectRequest(@Path("id") int id);
}
