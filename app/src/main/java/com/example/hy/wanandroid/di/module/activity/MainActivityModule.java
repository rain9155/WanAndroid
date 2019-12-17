package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.widget.dialog.GotoDetialDialog;
import com.example.hy.wanandroid.widget.dialog.OpenBrowseDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * MainActivity的Module
 * Created by 陈健宇 at 2018/10/26
 */
@Module
public class MainActivityModule {

    @Provides
    @PerActivity
    Fragment[] provideFragments(){
        return new BaseFragment[5];
    }

    @Provides
    @PerActivity
    VersionDialog provideVersionDialog(){
        return new VersionDialog();
    }

    @Provides
    @PerActivity
    OpenBrowseDialog provideOpenBrowseDialog(){
        return new OpenBrowseDialog();
    }

    @Provides
    @PerActivity
    GotoDetialDialog provideGotoDetialDialog(){
        return new GotoDetialDialog();
    }

}
