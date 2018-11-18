package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.MineFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.mine.MineFragment;

import dagger.Subcomponent;

/**
 * MineFragment的Component
 * Created by 陈健宇 at 2018/11/18
 */
@PerFragment
@Subcomponent(modules = MineFragmentModule.class)
public interface MineFragmentComponent {
    void inject(MineFragment mineFragment);
}
