package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.LoginActivityModule;
import com.example.hy.wanandroid.di.module.activity.RegisterActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.mine.RegisterActivity;

import dagger.Component;

/**
 * RegisterActivity的Component
 * Created by 陈健宇 at 2018/11/19
 */
@PerActivity
@Component(modules = RegisterActivityModule.class, dependencies = AppComponent.class)
public interface RegisterActivityComponent {
    void inject(RegisterActivity registerActivity);
}
