package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.network.api.homepager.HomeApis;
import com.example.hy.wanandroid.presenter.homepager.HomePresenter;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * HomeFragment的Module
 * Created by 陈健宇 at 2018/10/26
 */
@Module
public class HomeFragmentModule {

    @Provides
    @PerFragment
    @Named("bannerTitles")
    List<String> provideBannerTitles(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    @Named("bannerImages")
    List<String> provideBannerImages(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    public HomeApis provideHomeApis(Retrofit retrofit){
        return retrofit.create(HomeApis.class);
    }

}
