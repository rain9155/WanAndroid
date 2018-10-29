package com.example.hy.wanandroid.di.module.fragment;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * ProjectFragment的Module
 * Created by 陈健宇 at 2018/10/29
 */
@Module
public class ProjectFragmentModule {

    @Provides
    List<SupportFragment> provideSupportFragment(){
        return new ArrayList<>();
    }

    @Provides
    List<String> provideTitles(){
        return new ArrayList<>();
    }

    @Provides
    List<Integer> provideIds(){
        return new ArrayList<>();
    }

}
