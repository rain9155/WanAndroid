package com.example.hy.wanandroid.network.api;

import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.project.Project;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 项目所有的api
 * Created by 陈健宇 at 2018/10/29
 */
public interface ProjectApis {

    /**
     * 获得大概项目列表
     * http://www.wanandroid.com/project/tree/json
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<Project>>> getProjectList();

    /**
     * 获得详细项目列表
     * http://www.wanandroid.com/project/list/1/json?cid=294
     */
    @GET("project/list/{pageNum}/json")
    Observable<BaseResponse<Articles>> getProjects(@Path("pageNum") int pageNum,//页数
                                                    @Query("cid") int id//具体项目列表的id
    );

}
