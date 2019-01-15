package com.example.hy.wanandroid.event;

/**
 * 改变头像或背景事件
 * Created by 陈健宇 at 2019/1/7
 */
public class ChangeFaceEvent {

    private int mFlag;

    public ChangeFaceEvent(int flag) {
        this.mFlag = flag;
    }

    public int isChangeFace() {
        return mFlag;
    }
}
