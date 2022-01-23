package com.example.hy.wanandroid.model.prefs;

/**
 * SharedPreferences操作接口
 * Created by 陈健宇 at 2018/11/26
 */
public interface PreferencesHelper {

    //保存主页面状态
    void setCurMainItem(int position);
    //保存Wechats页面状态
    void setCurWechatItem(int position);
    //保存Projects页面状态
    void setCurProjectItem(int position);
    //获取主页面状态
    int getCurMainItem();
    //获取Wechats状态
    int getCurWechatItem();
    //获取Projects页面状态
    int getCurProjectItem();
    //保存无图设置
    void setNoImageState(boolean isNoImage);
    //获得无图设置
    boolean getNoImageState();
    //保存自动缓存设置
    void setAutoCacheState(boolean isAuto);
    //获得自动缓存设置
    boolean getAutoCacheState();
    //保存状态栏着色设置
    void setStatusBarState(boolean isStatusBar);
    //获得状态栏着色设置
    boolean getStatusBarState();
    //保存下载记录
    void setDownloadId(long id);
    //获得下载记录
    long getDownloadId();
    //保存网络状态
    void setNetWorkState(boolean isConnection);
    //获得网络状态
    boolean getNetWorkState();
    //保存自动更新状态
    void setAutoUpdateState(boolean isAuto);
    //获得自动更新状态
    boolean getAutoUpdateState();
    //获取选择的语言
    String getSelectedLanguage();
    //设置选择的语言
    void setSelectedLanguage(String lan);
    //保存选择的主题
    void setSelectedTheme(String theme);
    //获取保存的主题
    String getSelectedTheme();

}
