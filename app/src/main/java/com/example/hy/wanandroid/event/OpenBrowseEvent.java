package com.example.hy.wanandroid.event;

/**
 * Created by 陈健宇 at 2018/12/15
 */
public class OpenBrowseEvent {

    private String mApkUrl;

    public OpenBrowseEvent(String apkUrl) {
        mApkUrl = apkUrl;
    }

    public String getApkUrl() {
        return mApkUrl;
    }
}
