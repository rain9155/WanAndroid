package com.example.hy.wanandroid.entity;

import java.io.Serializable;

/**
 * 包含apk下载地址
 * Created by 陈健宇 at 2018/12/6
 */
public class Apk implements Serializable {

    private String mDownloadUrl;

    private String mName;
    
    private String mSize;

    private String mVersionName;

    private String mVersionBody;

    private boolean needUpdate;

    public Apk(String downloadUrl, String name, String size, String versionName, String versionBody, boolean needUpdate) {
        mDownloadUrl = downloadUrl;
        mName = name;
        mSize = size;
        mVersionName = versionName;
        mVersionBody = versionBody;
        this.needUpdate = needUpdate;
    }

    public String getDownloadUrl() {
        return mDownloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        mDownloadUrl = downloadUrl;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

    public String getVersionName() {
        return mVersionName;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public String getVersionBody() {
        return mVersionBody;
    }

    public void setVersionBody(String versionBody) {
        mVersionBody = versionBody;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}
