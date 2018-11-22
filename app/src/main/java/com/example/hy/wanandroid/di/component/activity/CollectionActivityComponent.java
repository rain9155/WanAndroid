package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.CollectionActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.mine.CollectionActivity;

import dagger.Component;

/**
 * CollectionActivity的Component
 * Created by 陈健宇 at 2018/11/22
 */
@PerActivity
@Component(modules = CollectionActivityModule.class, dependencies = AppComponent.class)
public interface CollectionActivityComponent {
    void inject(CollectionActivity collectionActivity);
}
