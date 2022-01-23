package com.example.hy.wanandroid.utlis;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.Window;
import android.view.WindowManager;
import java.util.Iterator;
import java.util.List;
import androidx.core.graphics.drawable.DrawableCompat;

/**
 * 一些公用方法工具类
 * Created by 陈健宇 at 2018/10/28
 */
public class CommonUtil{

    public static boolean isEmptyList(List<?> list) {
        return list == null || list.size() == 0;
    }

    public static void changeBackgoundAlpha(float alpha, Window window) {
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.alpha = alpha;
        window.setAttributes(lp);
    }

    public static Drawable getTintDrawable(Drawable originalDrawable, ColorStateList colors) {
        Drawable.ConstantState state = originalDrawable.getConstantState();
        Drawable tintDrawable = DrawableCompat.wrap(state == null ? originalDrawable : state.newDrawable()).mutate();
        tintDrawable.setBounds(0, 0, originalDrawable.getIntrinsicWidth(), originalDrawable.getIntrinsicHeight());
        DrawableCompat.setTintList(tintDrawable, colors);
        return tintDrawable;
    }

    /**
     * 获取进程名称
     */
    public static String getProcessName(Context cxt, int pid) {
        @SuppressLint("WrongConstant")
        ActivityManager am = (ActivityManager)cxt.getSystemService("activity");
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        } else {
            Iterator var4 = runningApps.iterator();
            ActivityManager.RunningAppProcessInfo procInfo;
            do {
                if (!var4.hasNext()) {
                    return null;
                }
                procInfo = (ActivityManager.RunningAppProcessInfo)var4.next();
            } while(procInfo.pid != pid);
            return procInfo.processName;
        }
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
