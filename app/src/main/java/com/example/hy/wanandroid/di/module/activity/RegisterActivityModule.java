package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.widget.dialog.LoadingDialog;

import dagger.Module;
import dagger.Provides;

/**
 * RegisterActivity的Module
 * Created by 陈健宇 at 2018/11/19
 */
@Module
public class RegisterActivityModule {

    @Provides
    @PerActivity
    LoadingDialog provideLoadingDialog(){
        return new LoadingDialog();
    }

}
