package com.example.hy.wanandroid.di.module.activity;



import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchySecondPresenter;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import dagger.Module;
import dagger.Provides;

/**
 * HierarchySecondActivity的Module
 * Created by 陈健宇 at 2018/10/29
 */
@Module
public class HierarchySecondActivityModule {

    @Provides
    List<Fragment> provideSupportFragment(){
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    List<String> provideTitles(){
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    List<Integer> provideIds(){
        return new ArrayList<>();
    }

}
