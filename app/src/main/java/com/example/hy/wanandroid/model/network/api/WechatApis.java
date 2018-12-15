package com.example.hy.wanandroid.model.network.api;

import com.example.hy.wanandroid.model.network.entity.Articles;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Tab;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public interface WechatApis {

    /**
     * 获得公众号tab
     * http://wanandroid.com/wxarticle/chapters/json
     */
    @GET("wxarticle/chapters/json")
    Observable<BaseResponse<List<Tab>>> getWechatTabs();

    /**
     * 获得某个公共号的文章列表
     * http://wanandroid.com/wxarticle/list/408/2/json
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<Articles>> getWechatArticles(@Path("id") int id, //某个公众号id
                                                         @Path("pageNum") int pageNum//某个公众号的页码
    );

    /**
     * 获得某个公共号下的搜索结果
     * http://wanandroid.com/wxarticle/list/408/1/json?k=java
     */
    @GET("wxarticle/list/{id}/{pageNum}/json")
    Observable<BaseResponse<Articles>> getWechatSearchResults(@Path("id") int id, //某个公众号id
                                                              @Path("pageNum") int pageNum,//某个公众号的页码
                                                              @Query("k") int key//关键字
    );
}
