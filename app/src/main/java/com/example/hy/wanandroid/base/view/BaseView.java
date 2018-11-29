package com.example.hy.wanandroid.base.view;

/**
 * View接口
 * Created by 陈健宇 at 2018/10/21
 */
public interface BaseView {

    //显示加载错误布局
    void showErrorView();

    //显示错误信息
    void showErrorMes();

    //重新加载
    void reLoad();

    //显示加载中布局
    void showLoading();

    void showNormalView();

    //显示dialog
    void showDialog();

    //显示Toast
    void showToast(String toast);

    //显示SnackBar
    void showSnackBar(String toast);

    //禁止加载
    void unableRefresh();

    //夜间模式
    void userNightNode(boolean isNight);

    void tokenExpire(int requestCode);
}
