package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.HierarchyFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;

import dagger.Subcomponent;

/**
 * HierarchyFragment的Component
 * Created by 陈健宇 at 2018/10/28
 */
@PerFragment
@Subcomponent(modules = HierarchyFragmentModule.class)
public interface HierarchyFragmentComponent {

    void inject(HierarchyFragment hierarchyFragment);
}
