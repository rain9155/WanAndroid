package com.example.hy.wanandroid.network.api;

import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

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
}
