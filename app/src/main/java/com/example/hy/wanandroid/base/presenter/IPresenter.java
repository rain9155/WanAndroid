package com.example.hy.wanandroid.base.presenter;

import com.example.hy.wanandroid.base.view.BaseView;

import io.reactivex.disposables.Disposable;

/**
 * presenter接口
 * Created by 陈健宇 at 2018/10/21
 */
public interface IPresenter<T extends BaseView>{

    //注入View
    void attachView(T view);

    //判断是否注入了View
    boolean isAttachView();

    //解除View
    void detachView();

    //订阅事件，管理事件生命周期
    void addSubcriber(Disposable disposable);

    //订阅事件
    void subscribleEvent();

    boolean getNoImageState();
    boolean getAutoCacheState();
    boolean getNightModeState();
    boolean getStatusBarState();

}
