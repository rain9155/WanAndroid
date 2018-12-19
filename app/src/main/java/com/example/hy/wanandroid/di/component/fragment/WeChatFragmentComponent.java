package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.di.module.fragment.WeChatFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.wechat.WeChatFragment;

import dagger.Subcomponent;

/**
 * WeChatFragment的Component
 * Created by 陈健宇 at 2018/12/19
 */
@PerFragment
@Subcomponent(modules = WeChatFragmentModule.class)
public interface WeChatFragmentComponent {

    void inject(WeChatFragment weChatFragment);

}
