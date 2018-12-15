package com.example.hy.wanandroid.event;

/**
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdataEvent {

    private boolean isMain;

    public UpdataEvent(boolean isMain) {
        this.isMain = isMain;
    }

    public boolean isMain() {
        return isMain;
    }
}
