package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.CoinActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.mine.CoinsActivity;

import dagger.Component;

/**
 * CoinActivity的Component
 * Created by 陈健宇 at 2019/9/21
 */
@PerActivity
@Component(modules = CoinActivityModule.class, dependencies = AppComponent.class)
public interface CoinActivityComponent {
    void inject(CoinsActivity coinActivity);
}
