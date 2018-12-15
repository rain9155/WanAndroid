package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.widget.dialog.ClearCacheDialog;
import com.example.hy.wanandroid.widget.dialog.UpDataLoadingDialog;
import com.example.hy.wanandroid.widget.dialog.VersionDialog;

import java.io.File;

import javax.inject.Named;

import androidx.fragment.app.DialogFragment;
import dagger.Module;
import dagger.Provides;

/**
 * SettingsActivity的Module
 * Created by 陈健宇 at 2018/11/26
 */
@Module
public class SettingsActivityModule {

    @Provides
    @PerActivity
    File provideCacheFile(){
        return new File(Constant.PATH_NETCACHE);
    }

    @Provides
    @PerActivity
    VersionDialog provideVersionDialog(){
        return new VersionDialog();
    }

    @Provides
    @PerActivity
    ClearCacheDialog provideClearCacheDialog(){
        return new ClearCacheDialog();
    }


}
