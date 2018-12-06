package com.example.hy.wanandroid.event;

/**
 * 状态栏着色事件
 * Created by 陈健宇 at 2018/12/6
 */
public class StatusBarEvent {

    private boolean isSet;

    public StatusBarEvent(boolean isSet) {
        this.isSet = isSet;
    }

    public boolean isSet() {
        return isSet;
    }
}
