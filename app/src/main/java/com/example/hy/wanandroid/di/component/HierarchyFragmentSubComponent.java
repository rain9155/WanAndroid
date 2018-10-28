package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.di.module.HierarchyFragmentModule;
import com.example.hy.wanandroid.di.module.HomeFragmentModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;

import dagger.Subcomponent;

/**
 * HierarchyFragment的Component
 * Created by 陈健宇 at 2018/10/28
 */
@PerFragment
@Subcomponent(modules = HierarchyFragmentModule.class)
public interface HierarchyFragmentSubComponent {
    void inject(HierarchyFragment hierarchyFragment);
}
