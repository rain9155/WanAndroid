package com.example.hy.wanandroid.di.component.activity;

import com.example.hy.wanandroid.di.component.AppComponent;
import com.example.hy.wanandroid.di.component.fragment.HierarchyFragmentComponent;
import com.example.hy.wanandroid.di.component.fragment.HomeFragmentComponent;
import com.example.hy.wanandroid.di.component.fragment.ProjectFragmentComponent;
import com.example.hy.wanandroid.di.module.fragment.HierarchyFragmentModule;
import com.example.hy.wanandroid.di.module.fragment.HomeFragmentModule;
import com.example.hy.wanandroid.di.module.activity.MainActivityModule;
import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.project.ProjectFragment;

import dagger.Component;

/**
 * MainActivity的Component
 * Created by 陈健宇 at 2018/10/26
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    HomeFragmentComponent getHomFragmentSubComponent(HomeFragmentModule homeFragmentModule);
    HierarchyFragmentComponent getHierarchyFragmentSubComponent(HierarchyFragmentModule hierarchyFragmentModule);
    ProjectFragmentComponent getProjectFragmentComponent(ProjectFragmentModule projectFragmentModule);
}
