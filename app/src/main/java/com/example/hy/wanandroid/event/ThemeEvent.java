package com.example.hy.wanandroid.event;

/**
 * 夜间模式事件
 * Created by 陈健宇 at 2018/11/26
 */
public class ThemeEvent {

    private String theme;

    public ThemeEvent(String theme) {
        this.theme = theme;
    }

    public String getTheme() {
        return theme;
    }
}
