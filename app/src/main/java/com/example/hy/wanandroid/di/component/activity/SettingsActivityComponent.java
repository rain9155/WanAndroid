package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.mine.SettingsActivity;

import dagger.Component;

/**
 * SettingsActivity的Component
 * Created by 陈健宇 at 2018/11/26
 */
@PerActivity
@Component(dependencies = AppComponent.class)
public interface SettingsActivityComponent {
    void inject(SettingsActivity settingsActivity);
}
