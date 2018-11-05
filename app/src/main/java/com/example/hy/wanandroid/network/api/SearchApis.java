package com.example.hy.wanandroid.network.api;

import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.search.HotKey;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * 搜索tab中所有的api
 * Created by 陈健宇 at 2018/11/2
 */
public interface SearchApis {

    /**
     * 搜索
     * http://www.wanandroid.com/article/query/0/json
     */
    @POST("article/query/{pageNum}/json")
    @FormUrlEncoded
    Observable<BaseResponse<Articles>> getSearchRequest(@Field("k") String key,//关键字
                                                        @Path("pageNum") int pageNum//页数
    );


    /**
     * 获取搜索热词
     * http://www.wanandroid.com//hotkey/json
     */
    @GET("hotkey/json")
    Observable<BaseResponse<List<HotKey>>> getHotKey();

}
