package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;

import dagger.Component;

/**
 * ArticleActivity的Component
 * Created by 陈健宇 at 2018/11/23
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface ArticleActivityComponent {
    void inject(ArticleActivity articleActivity);
}
