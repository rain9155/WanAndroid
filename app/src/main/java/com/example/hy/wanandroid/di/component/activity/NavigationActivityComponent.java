package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.NavigationActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;

import dagger.Component;

/**
 * NavigationActivity的Component
 * Created by 陈健宇 at 2018/10/31
 */
@PerActivity
@Component(modules = NavigationActivityModule.class, dependencies = AppComponent.class)
public interface NavigationActivityComponent {
    void inject(NavigationActivity navigationActivity);
}
