package com.example.hy.wanandroid.utlis;

import com.example.hy.wanandroid.BuildConfig;

/**
 * log工具类
 * Create by 陈健宇 at 2018/8/23
 */
public class LogUtil {

    private static final boolean DEBUG = BuildConfig.DEBUG;

    public static void v(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        android.util.Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        android.util.Log.e(tag, msg);
    }

    private LogUtil() throws Exception {
        throw new AssertionError();
    }
}
