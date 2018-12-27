package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.component.fragment.HierarchySecondFragmentComponent;
import com.example.hy.wanandroid.di.module.activity.HierarchySecondActivityModule;
import com.example.hy.wanandroid.di.module.fragment.HierarchySecondFragmentModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.hierarchy.HierarchySecondActivity;

import dagger.Component;

/**
 * HierarchySecondActivity的Component
 * Created by 陈健宇 at 2018/10/29
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = HierarchySecondActivityModule.class)
public interface HierarchySecondActivityComponent {

    void inject(HierarchySecondActivity hierarchySecondActivity);

    HierarchySecondFragmentComponent getHierarchySecondFragmentComponent(HierarchySecondFragmentModule hierarchySecondFragmentModule);
}
