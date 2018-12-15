package com.example.hy.wanandroid.model.network.api;

import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.FirstHierarchy;
import com.example.hy.wanandroid.model.network.entity.SecondHierarchy;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 体系的所有api
 * Created by 陈健宇 at 2018/10/28
 */
public interface HierarchyApis {

    /**
     * 获取一级体系
     * http://www.wanandroid.com/tree/json
     */
    @GET("tree/json")
    Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList();

    /**
     * 获取二级体系文章
     * http://www.wanandroid.com/article/list/0/json?cid=60
     */
    @GET("article/list/{pageNum}/json")
    Observable<BaseResponse<SecondHierarchy>> getSecondHierarchyList(@Path("pageNum") int pageNum,//页数
                                                                    @Query("cid") int id);//一级体系的id
}
