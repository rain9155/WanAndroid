package com.example.hy.wanandroid.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.view.View;
import com.example.hy.wanandroid.config.App;

/**
 * 切换控件显隐性动画工具
 * Created by 陈健宇 at 2018/11/19
 */
public class AnimUtil {

    private AnimUtil() {
        throw new AssertionError();
    }

    /**
     * 通过透明度显示控件
     */
    public static void showByAlpha(View view){
        int shortAnimTime = App.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        view.setVisibility(View.VISIBLE);
        view.animate().alpha(1).setDuration(shortAnimTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.VISIBLE);
            }
        }).start();
    }

    /**
     * 通过透明度隐藏控件
     */
    public static void hideByAlpha(View view){
        int shortAnimTime = App.getContext().getResources().getInteger(android.R.integer.config_shortAnimTime);
        view.animate().alpha(0).setDuration(shortAnimTime).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.INVISIBLE);
            }
        }).start();
    }
}
