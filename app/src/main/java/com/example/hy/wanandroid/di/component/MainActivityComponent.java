package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.di.module.HomeFragmentModule;
import com.example.hy.wanandroid.di.module.MainActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;

import dagger.Component;

/**
 * MainActivity的Component
 * Created by 陈健宇 at 2018/10/26
 */
@PerActivity
@Component( dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    HomeFragmentSubComponent getHomFragmentSubComponent();

}
