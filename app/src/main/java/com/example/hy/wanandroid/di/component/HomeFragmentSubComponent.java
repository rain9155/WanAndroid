package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.di.module.HomeFragmentModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.homepager.HomeFragment;

import dagger.Subcomponent;

/**
 * Created by 陈健宇 at 2018/10/26
 */

@PerFragment
@Subcomponent
public interface HomeFragmentSubComponent {

    void inject(HomeFragment homeFragment);
}
