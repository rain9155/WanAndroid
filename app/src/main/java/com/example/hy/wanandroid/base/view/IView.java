package com.example.hy.wanandroid.base.view;

/**
 * View接口
 * Created by 陈健宇 at 2018/10/21
 */
public interface IView {

    //显示加载错误布局
    void showErrorView();

    //显示错误信息
    void showErrorMes();

    //重新加载
    void reLoad();

    //显示加载中布局
    void showLoading();

    //显示Toast
    void showToast();

    //显示SnackBar
    void showSnackBar();

}
