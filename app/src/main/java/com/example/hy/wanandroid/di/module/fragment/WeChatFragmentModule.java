package com.example.hy.wanandroid.di.module.fragment;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ProjectsAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.model.network.entity.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * Created by 陈健宇 at 2018/12/19
 */
@Module
public class WeChatFragmentModule {

    @Provides
    @PerFragment
    List<Fragment> provideSupportFragment(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    List<String> provideTitles(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    List<Integer> provideIds(){
        return new ArrayList<>();
    }

}
