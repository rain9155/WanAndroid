package com.example.hy.wanandroid.di.module.fragment;

import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.widget.dialog.ChangeFaceDialog;
import com.example.hy.wanandroid.widget.dialog.LogoutDialog;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 陈健宇 at 2018/11/18
 */
@Module
public class MineFragmentModule {

    @Provides
    @PerFragment
    LogoutDialog provideLogoutDialog(){
        return new LogoutDialog();
    }

    @Provides
    @PerFragment
    ChangeFaceDialog provideChangeFaceDialog(){
        return new ChangeFaceDialog();
    }

}
