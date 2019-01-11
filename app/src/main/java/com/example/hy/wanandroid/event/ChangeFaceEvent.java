package com.example.hy.wanandroid.event;

/**
 * 改变头像或背景事件
 * Created by 陈健宇 at 2019/1/7
 */
public class ChangeFaceEvent {

    private boolean isChangeFace;

    public ChangeFaceEvent(boolean isChangeFace) {
        this.isChangeFace = isChangeFace;
    }

    public boolean isChangeFace() {
        return isChangeFace;
    }
}
