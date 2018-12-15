package com.example.hy.wanandroid.event;

/**
 * 设置界面切换夜间模式事件
 * Created by 陈健宇 at 2018/12/11
 */
public class SettingsNightModeEvent {

    private boolean isNightMode;

    public SettingsNightModeEvent(boolean isNightMode) {
        this.isNightMode = isNightMode;
    }

    public boolean isNightMode() {
        return isNightMode;
    }
}
