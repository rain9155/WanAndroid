package com.example.hy.wanandroid.base.view;

import android.app.Activity;
import android.content.Context;

/**
 * 通用View接口
 * Created by 陈健宇 at 2018/10/21
 */
public interface IView {

    //显示加载错误布局
    default void showErrorView(){}

    //重新加载
    default void reLoad(){}

    //显示加载中布局
    default void showLoading(){}

    //显示空布局
    default void showEmptyView(){}

    //显示内容布局
    default void showNormalView(){}

    //显示错误信息
    void showErrorMes();

    //显示dialog
    void showDialog();

    //显示Toast
    void showToast(String toast);

    //显示Toast
    void showToast(Activity activity, String toast);

    //禁止加载
    void unableRefresh();

    //设置状态栏颜色
    void setStatusBarColor(boolean isSet);

    //显示网络状态信息
    void showTipsView(boolean isConnection);
}
