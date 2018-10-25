package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.presenter.homepager.HomePresenter;

import dagger.Module;
import dagger.Provides;

/**
 * HomeFragment的Module
 * Created by 陈健宇 at 2018/10/26
 */
@Module
public class HomeFragmentModule {

    @Provides
    HomePresenter provideHomePresenter(){
        return new HomePresenter();
    }

}
