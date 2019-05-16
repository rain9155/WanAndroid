package com.example.hy.wanandroid.event;

/**
 * 切换语言事件
 * Created by 陈健宇 at 2019/5/5
 */
public class LanguageEvent {

    private String language;

    public LanguageEvent(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language == null ? "" : language;
    }
}
