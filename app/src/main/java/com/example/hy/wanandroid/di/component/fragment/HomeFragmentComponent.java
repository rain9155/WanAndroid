package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.HomeFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;

import dagger.Subcomponent;

/**
 * HomeFragment的Component
 * Created by 陈健宇 at 2018/10/26
 */
@PerFragment
@Subcomponent(modules = HomeFragmentModule.class)
public interface HomeFragmentComponent {

    void inject(HomeFragment homeFragment);
}
