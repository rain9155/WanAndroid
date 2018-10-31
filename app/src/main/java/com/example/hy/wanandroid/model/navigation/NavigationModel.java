package com.example.hy.wanandroid.model.navigation;

import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.network.api.NavigationApis;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.navigation.Tag;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 导航的model
 * Created by 陈健宇 at 2018/10/31
 */
public class NavigationModel implements NavigationContract.Model {

    private NavigationApis mNavigationApis;

    @Inject
    public NavigationModel(NavigationApis navigationApis) {
        mNavigationApis = navigationApis;
    }

    @Override
    public Observable<BaseResponse<List<Tag>>> getTags() {
        return mNavigationApis.getTags();
    }
}
