package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.module.activity.SearchActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;

import dagger.Component;

/**
 * Created by 陈健宇 at 2018/11/2
 */
@PerActivity
@Component(modules = SearchActivityModule.class, dependencies = AppComponent.class)
public interface SearchActivityComponent {
    void inject(SearchActivity searchActivity);
}
