package com.example.hy.wanandroid.model.network.api;

import com.example.hy.wanandroid.model.network.entity.Version;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * 获取版本的api
 * Created by 陈健宇 at 2018/12/7
 */
public interface VersionApi {
    /**
     * 版本更新
     * https://api.github.com/repos/rain9155/WanAndroid/releases/latest
     */
    @GET("https://api.github.com/repos/rain9155/WanAndroid/releases/latest")
    Observable<Version> getVersionDetial();

    /**
     * apk下载
     * https://github.com/rain9155/WanAndroid/releases/download/v1.0/app-release.apk
     */
}
