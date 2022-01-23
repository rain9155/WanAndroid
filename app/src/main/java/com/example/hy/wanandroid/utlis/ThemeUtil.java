package com.example.hy.wanandroid.utlis;

import android.content.Context;
import android.content.res.Configuration;

import androidx.appcompat.app.AppCompatDelegate;

/**
 * 主题切换工具
 * @see <a herf="https://developer.android.com/guide/topics/ui/look-and-feel/darktheme">Dark Theme</a>
 * @author chenjianyu
 * @date 1/22/22
 */
public class ThemeUtil {

    public static final String SYSTEM = "system";

    public static final String DARK = "dark";

    public static final String LIGHT = "light";

    public static boolean isDarkTheme(Context context) {
        Configuration configuration = context.getResources().getConfiguration();
        return (configuration.uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
    }

    public static void setTheme(Context context, String theme) {
        int mode = DARK.equals(theme)
                ? AppCompatDelegate.MODE_NIGHT_YES : LIGHT.equals(theme)
                ? AppCompatDelegate.MODE_NIGHT_NO
                : AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
        AppCompatDelegate.setDefaultNightMode(mode);
    }

}
