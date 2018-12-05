package com.example.hy.wanandroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hy.wanandroid.R;

import q.rorbin.badgeview.DisplayUtil;

/**
 * Created by 陈健宇 at 2018/12/5
 */
public class ToastUtil extends com.example.utilslibrary.ToastUtil {

    @SuppressLint({"ResourceAsColor"})
    public static void toastInBottom(Context context, String message, View toastView) {
        if (toastView == null) {
            toastView = LayoutInflater.from(context).inflate(R.layout.toast, null);
            TextView textView = toastView.findViewById(R.id.tv_toast);
            textView.setText(message);
        }

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.BOTTOM, 0, DisplayUtil.dp2px(context, 50));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

}
