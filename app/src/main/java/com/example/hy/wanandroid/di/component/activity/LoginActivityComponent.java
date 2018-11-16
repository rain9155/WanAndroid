package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.LoginActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.mine.LoginActivity;

import dagger.Component;

/**
 * LoginActivity的Component
 * Created by 陈健宇 at 2018/11/16
 */
@PerActivity
@Component(modules = LoginActivityModule.class, dependencies = AppComponent.class)
public interface LoginActivityComponent {
    void inject(LoginActivity loginActivity);
}
