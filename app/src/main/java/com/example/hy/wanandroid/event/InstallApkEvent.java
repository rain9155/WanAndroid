package com.example.hy.wanandroid.event;

import android.net.Uri;

/**
 * 安装apk事件
 * Created by 陈健宇 at 2019/1/7
 */
public class InstallApkEvent {

    public String mApkUri;

    public InstallApkEvent(String apkUri) {
        mApkUri = apkUri;
    }

    public String getApkUri() {
        return mApkUri;
    }
}
