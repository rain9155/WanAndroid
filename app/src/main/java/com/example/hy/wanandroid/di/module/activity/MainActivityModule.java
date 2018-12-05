package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.di.scope.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * MainActivity的Module
 * Created by 陈健宇 at 2018/10/26
 */
@Module
public class MainActivityModule {

    @Provides
    @PerActivity
    BaseFragment[] provideFragments(){
        return new BaseFragment[4];
    }

}
