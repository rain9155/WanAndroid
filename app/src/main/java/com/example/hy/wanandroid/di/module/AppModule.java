package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.db.DbHelper;
import com.example.hy.wanandroid.model.network.NetworkHelper;
import com.example.hy.wanandroid.model.network.api.VersionApi;
import com.example.hy.wanandroid.model.network.api.WechatApis;
import com.example.hy.wanandroid.model.network.entity.Version;
import com.example.hy.wanandroid.model.network.interceptor.CacheInterceptor;
import com.example.hy.wanandroid.model.network.interceptor.ReadCookiesInterceptor;
import com.example.hy.wanandroid.model.network.interceptor.WriteCookiesInterceptor;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.model.network.api.HierarchyApis;
import com.example.hy.wanandroid.model.network.api.HomeApis;
import com.example.hy.wanandroid.model.network.api.MineApis;
import com.example.hy.wanandroid.model.network.api.NavigationApis;
import com.example.hy.wanandroid.model.network.api.ProjectApis;
import com.example.hy.wanandroid.model.network.api.SearchApis;
import com.example.hy.wanandroid.model.network.gson.CustomGsonConverterFactory;
import com.example.hy.wanandroid.model.prefs.PreferencesHelper;
import com.example.utilslibrary.FileUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

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
    App provideApp(){
        return mApp;
    }

    @Provides
    @Singleton
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){

        //设置缓存
        File cacheDir = new File(Constant.PATH_NETCACHE);
        Cache cache = new Cache(cacheDir, 1024 * 1024 * 10);//缓存的最大尺寸10M
        builder.cache(cache);
        builder.addInterceptor(new CacheInterceptor());

        //错误重连
        builder.retryOnConnectionFailure(true);
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //cookie认证
        builder.addInterceptor(new ReadCookiesInterceptor(App.getContext()));
        builder.addInterceptor(new WriteCookiesInterceptor(App.getContext()));
        return builder.build();
    }


    @Provides
    @Singleton
     Retrofit provideRetrofit(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(CustomGsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("version")
    Retrofit provideRetrofitForVersion(OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
     HierarchyApis provideHierarchyApis(Retrofit retrofit){
        return retrofit.create(HierarchyApis.class);
    }

    @Provides
    @Singleton
     HomeApis provideHomeApis(Retrofit retrofit){
        return retrofit.create(HomeApis.class);
    }

    @Provides
    @Singleton
    ProjectApis provideProjectApis(Retrofit retrofit){
        return retrofit.create(ProjectApis.class);
    }

    @Provides
    @Singleton
     NavigationApis provideNavigationApis(Retrofit retrofit){
        return retrofit.create(NavigationApis.class);
    }

    @Provides
    @Singleton
     SearchApis provideSearchApis(Retrofit retrofit){
        return retrofit.create(SearchApis.class);
    }

    @Provides
    @Singleton
     MineApis provideMineApis(Retrofit retrofit){
        return retrofit.create(MineApis.class);
    }

    @Provides
    @Singleton
    WechatApis provideWechatApis(Retrofit retrofit){
        return retrofit.create(WechatApis.class);
    }

    @Provides
    @Singleton
    VersionApi provideVersionApi(@Named("version") Retrofit retrofit){
        return retrofit.create(VersionApi.class);
    }
}
