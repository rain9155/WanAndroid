package com.example.hy.wanandroid.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

/**
 * Sanckbar提示
 * Created by 陈健宇 at 2018/11/4
 */
public class SnackUtil{

    private static Snackbar snackbar;

    public static void showSnackBar(Activity activity, String message){
        if(snackbar == null){
            snackbar = Snackbar.make(activity.getWindow().getDecorView(), message, Snackbar.LENGTH_SHORT);
        }else {
            snackbar.setText(message);
        }
        snackbar.show();
    }

    private SnackUtil() {
        throw new AssertionError();
    }

}
