package com.example.hy.wanandroid.network.api;

import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.navigation.Tag;

import java.util.List;

import io.reactivex.Observable;

/**
 * 导航tab的所有api
 * Created by 陈健宇 at 2018/10/31
 */
public interface NavigationApis {

    /**
     * 获得导航标签列表
     * http://www.wanandroid.com/navi/json
     */
    Observable<BaseResponse<List<Tag>>> getTags();

}
