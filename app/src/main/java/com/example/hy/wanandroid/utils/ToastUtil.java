package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 提示微技巧
 * Created by 陈健宇 at 2018/10/27
 */
public class ToastUtil {

    private static Toast toast;

    /**
     * Toast提示
     * @param context
     * @param message 的内容
     */
    public static void showToast(Context context, String message){
        if(toast == null){
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else {
            toast.setText(message);
        }
        toast.show();
    }

    private ToastUtil() {
        throw new AssertionError();
    }
}
