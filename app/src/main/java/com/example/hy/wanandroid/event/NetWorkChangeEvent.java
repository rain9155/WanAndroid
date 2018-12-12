package com.example.hy.wanandroid.event;

/**
 * Created by 陈健宇 at 2018/12/12
 */
public class NetWorkChangeEvent {

    private boolean isConnection;

    public NetWorkChangeEvent(boolean isConnection) {
        this.isConnection = isConnection;
    }

    public boolean isConnection() {
        return isConnection;
    }
}
