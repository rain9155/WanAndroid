package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.model.network.api.VersionApi;
import com.example.hy.wanandroid.model.network.api.WechatApis;
import com.example.hy.wanandroid.model.network.cookie.CookieManger;
import com.example.hy.wanandroid.model.network.interceptor.CacheInterceptor;
import com.example.hy.wanandroid.model.network.api.HierarchyApis;
import com.example.hy.wanandroid.model.network.api.HomeApis;
import com.example.hy.wanandroid.model.network.api.MineApis;
import com.example.hy.wanandroid.model.network.api.NavigationApis;
import com.example.hy.wanandroid.model.network.api.ProjectApis;
import com.example.hy.wanandroid.model.network.api.SearchApis;
import com.example.hy.wanandroid.model.network.gson.CustomGsonConverterFactory;
import com.example.hy.wanandroid.model.network.ssl.SSLSocketCompatFactory;
import com.example.hy.wanandroid.model.network.ssl.TrustAllCert;
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
 * 网络相关的module
 * Created by 陈健宇 at 2018/10/26
 */
@Module()
public class NetworkModule {

    @Provides
    OkHttpClient.Builder provideOkHttpClientBuilder(){
        return new OkHttpClient.Builder();
    }

    @Provides
    OkHttpClient provideOkHttpClient(OkHttpClient.Builder builder){
        //设置ssl
        TrustAllCert trustAllCert = new TrustAllCert();
        builder.sslSocketFactory(new SSLSocketCompatFactory(trustAllCert), trustAllCert);

        //设置缓存
        File cacheDir = new File(Constant.PATH_NET);
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
        //builder.addInterceptor(new ReadCookiesInterceptor(App.getContext()));
        //builder.addInterceptor(new WriteCookiesInterceptor(App.getContext()));
        builder.cookieJar(new CookieManger(App.getContext()));
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
     HierarchyApis provideHierarchyApis(Retrofit retrofit){
        return retrofit.create(HierarchyApis.class);
    }

    @Provides
     HomeApis provideHomeApis(Retrofit retrofit){
        return retrofit.create(HomeApis.class);
    }

    @Provides
    ProjectApis provideProjectApis(Retrofit retrofit){
        return retrofit.create(ProjectApis.class);
    }

    @Provides
     NavigationApis provideNavigationApis(Retrofit retrofit){
        return retrofit.create(NavigationApis.class);
    }

    @Provides
     SearchApis provideSearchApis(Retrofit retrofit){
        return retrofit.create(SearchApis.class);
    }

    @Provides
     MineApis provideMineApis(Retrofit retrofit){
        return retrofit.create(MineApis.class);
    }

    @Provides
    WechatApis provideWechatApis(Retrofit retrofit){
        return retrofit.create(WechatApis.class);
    }

    @Provides
    VersionApi provideVersionApi(@Named("version") Retrofit retrofit){
        return retrofit.create(VersionApi.class);
    }

}
