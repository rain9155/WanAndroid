package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.HierarchyFragmentModule;
import com.example.hy.wanandroid.di.module.fragment.HierarchySecondFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.hierarchy.HierarchySecondActivity;
import com.example.hy.wanandroid.view.hierarchy.HierarchySecondFragment;

import dagger.Subcomponent;

/**
 * HierarchySecondFragment的Component
 * Created by 陈健宇 at 2018/10/29
 */
@PerFragment
@Subcomponent(modules = HierarchySecondFragmentModule.class)
public interface HierarchySecondFragmentComponent {
    void inject(HierarchySecondFragment hierarchySecondFragment);
}
