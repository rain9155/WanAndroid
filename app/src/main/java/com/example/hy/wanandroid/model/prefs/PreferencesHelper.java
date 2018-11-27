package com.example.hy.wanandroid.model.prefs;

/**
 * SharedPreferences操作接口
 * Created by 陈健宇 at 2018/11/26
 */
public interface PreferencesHelper {

    //保存夜间模式设置
    void setNightModeState(boolean isNight);
    //取得夜间模式设置
    boolean getNightModeState();
    //保存页面状态
    void setCurrentItem(int position);
    //获取页面状态
    int getCurrentItem();
    //保存无图设置
    void setNoImageState(boolean isNoImage);
    //获得无图设置
    boolean getNoImageState();
    //保存自动缓存设置
    void setAutoCacheState(boolean isAuto);
    //获得自动缓存设置
    boolean getAutoCacheState();

}
