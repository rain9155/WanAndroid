package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.WeChatFragmentModule;
import com.example.hy.wanandroid.di.module.fragment.WeChatsFragmentModule;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.view.wechat.WeChatsFragment;

import dagger.Subcomponent;

/**
 * WeChatsFragment的Component
 * Created by 陈健宇 at 2018/12/19
 */
@PerFragment
@Subcomponent(modules = WeChatsFragmentModule.class)
public interface WeChatsFragmentComponent {
    void inject(WeChatsFragment weChatsFragment);
}
