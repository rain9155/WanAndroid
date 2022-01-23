package com.example.hy.wanandroid.utlis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.hy.wanandroid.component.UpdateService;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.entity.Apk;

import java.io.File;

/**
 * 下载工具
 * Created by 陈健宇 at 2018/12/7
 */
public class DownloadUtil {

    /**
     * 使用DownloadManager下载apk，如果不能则使用系统浏览器
     */
    public static boolean downloadApk(Context context, Apk apk) {
        if(!canDownloadState(context)) {
            return false;
        }
        Intent intent = new Intent(context, UpdateService.class);
        intent.putExtra(Constant.KEY_URL_APK, apk.getDownloadUrl());
        context.startService(intent);
        return true;
    }

    /**
     * 判断是否可以使用DownloadManager
     */
    private static boolean canDownloadState(Context ctx) {
        try {
            int state = ctx.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
            if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
