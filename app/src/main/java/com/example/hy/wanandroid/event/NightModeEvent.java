package com.example.hy.wanandroid.event;

/**
 * 夜间模式事件
 * Created by 陈健宇 at 2018/11/26
 */
public class NightModeEvent {

    private boolean isNight;

    public NightModeEvent(boolean isNight) {
        this.isNight = isNight;
    }

    public boolean isNight() {
        return isNight;
    }
}
