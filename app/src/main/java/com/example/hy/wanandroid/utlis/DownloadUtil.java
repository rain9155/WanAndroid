package com.example.hy.wanandroid.utlis;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.commonlib.utils.LogUtil;
import com.example.commonlib.utils.ShareUtil;
import com.example.hy.wanandroid.component.UpdataService;
import com.example.hy.wanandroid.config.Constant;

import java.io.File;

/**
 * 下载工具
 * Created by 陈健宇 at 2018/12/7
 */
public class DownloadUtil {

    /**
     * 下载apk
     */
    public static void downloadApk(Context context, String newVersionName) {
        String url = Constant.BASE_APK_URL + newVersionName + "/app-release.apk";
        Constant.NEW_VERSION_URL = url;
        if(canDownloadState(context)){
            LogUtil.d(LogUtil.TAG_COMMON, "DownloadManager可用");
            Intent intent = new Intent(context, UpdataService.class);
            intent.putExtra(Constant.KEY_URL_APK, url);
            context.startService(intent);
        }else {
            LogUtil.d(LogUtil.TAG_COMMON, "DownloadManager不可用");
            File file = new File(Constant.PATH_APK_2);
            if(file.exists()) file.delete();
            ShareUtil.openBrowser(context, url);
        }
    }

    /**
     * 是否可以使用DownloadManager,如果不能则使用系统浏览器
     */
    public static boolean canDownloadState(Context ctx) {
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

    /**
     * 获取版本号
     */
    public static String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        String version = "";
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            version = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


}
