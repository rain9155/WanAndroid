package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.widget.dialog.LoadingDialog;

import dagger.Module;
import dagger.Provides;

/**
 * LoginActivity的Module
 * Created by 陈健宇 at 2018/11/16
 */
@Module
public class LoginActivityModule {

    @Provides
    @PerActivity
    LoadingDialog provideLoadingDialog(){
        return new LoadingDialog();
    }
}
