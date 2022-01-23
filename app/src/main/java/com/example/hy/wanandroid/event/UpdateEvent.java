package com.example.hy.wanandroid.event;

import com.example.hy.wanandroid.entity.Apk;

/**
 * Created by 陈健宇 at 2018/12/7
 */
public class UpdateEvent {

    private Apk mApk;

    public UpdateEvent(Apk apk) {
        mApk = apk;
    }

    public Apk getApk() {
        return mApk;
    }

    public void setApk(Apk apk) {
        mApk = apk;
    }
}
