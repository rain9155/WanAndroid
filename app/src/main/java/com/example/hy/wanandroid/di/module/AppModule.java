package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.network.api.HierarchyApis;
import com.example.hy.wanandroid.network.api.HomeApis;
import com.example.hy.wanandroid.network.api.MineApis;
import com.example.hy.wanandroid.network.api.NavigationApis;
import com.example.hy.wanandroid.network.api.ProjectApis;
import com.example.hy.wanandroid.network.api.SearchApis;
import com.example.hy.wanandroid.network.gson.CustomGsonConverterFactory;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * App的module
 * Created by 陈健宇 at 2018/10/26
 */
@Module
public class AppModule {

    private final App mApp;

    public AppModule(App app) {
        mApp = app;
    }

    @Provides
    @Singleton
    public App provideApp(){
        return mApp;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public HierarchyApis provideHierarchyApis(Retrofit retrofit){
        return retrofit.create(HierarchyApis.class);
    }

    @Provides
    @Singleton
    public HomeApis provideHomeApis(Retrofit retrofit){
        return retrofit.create(HomeApis.class);
    }

    @Provides
    @Singleton
    public ProjectApis provideProjectApis(Retrofit retrofit){
        return retrofit.create(ProjectApis.class);
    }

    @Provides
    @Singleton
    public NavigationApis provideNavigationApis(Retrofit retrofit){
        return retrofit.create(NavigationApis.class);
    }

    @Provides
    @Singleton
    public SearchApis provideSearchApis(Retrofit retrofit){
        return retrofit.create(SearchApis.class);
    }

    @Provides
    @Singleton
    public MineApis provideMineApis(Retrofit retrofit){
        return retrofit.create(MineApis.class);
    }

}
