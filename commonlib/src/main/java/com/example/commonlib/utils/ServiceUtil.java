package com.example.commonlib.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.ArrayList;

/**
 * 服务工具类
 * Created by 陈健宇 at 2018/12/7
 */
public class ServiceUtil {

        /**
         * 判断服务是否开启
         */
        public static boolean isServiceRunning(Context context, String ServiceName) {
            if (("").equals(ServiceName) || ServiceName == null)
                return false;
            ActivityManager myManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) myManager.getRunningServices(30);
            for (int i = 0; i < runningService.size(); i++) {
                if (runningService.get(i).service.getClassName().equals(ServiceName)) {
                    return true;
                }
            }
            return false;
        }
}
