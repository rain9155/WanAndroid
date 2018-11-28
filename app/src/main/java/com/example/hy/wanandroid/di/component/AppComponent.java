package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.module.AppModule;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.api.HierarchyApis;
import com.example.hy.wanandroid.model.network.api.HomeApis;
import com.example.hy.wanandroid.model.network.api.MineApis;
import com.example.hy.wanandroid.model.network.api.NavigationApis;
import com.example.hy.wanandroid.model.network.api.ProjectApis;
import com.example.hy.wanandroid.model.network.api.SearchApis;

import javax.inject.Singleton;
import dagger.Component;
import retrofit2.Retrofit;

/**
 * App的component
 * Created by 陈健宇 at 2018/10/26
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(App app);//注入App

    App getApp();

    DataModel getDataModel();

    Retrofit getRetrofit();
    HomeApis getHomeApis();
    HierarchyApis getHierarchyApis();
    ProjectApis getProjectApis();
    NavigationApis getNavigationApis();
    SearchApis getSearchApis();
    MineApis getMineApis();
}
