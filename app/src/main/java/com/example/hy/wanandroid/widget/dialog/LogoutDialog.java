package com.example.hy.wanandroid.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.LoginEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 退出登陆提示框
 * Created by 陈健宇 at 2018/11/18
 */
public class LogoutDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        @SuppressLint("InflateParams")
        TextView content = new TextView(getContext());
        content.setText(R.string.mineFragment_confirm_logout);
        content.setTextColor(getResources().getColor(R.color.primaryText));
        content.setTextSize(16);
        content.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        content.setPadding(80, 60, 10, 10);
        content.setGravity(Gravity.LEFT);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setIcon(R.drawable.ic_toast)
                .setTitle(R.string.mineFragment_logout_toast)
                .setView(content)//设置内容部分
                .setNegativeButton(R.string.mineFragment_logout_cancel, (dialog1, which) -> this.dismiss())
                .setPositiveButton(R.string.mineFragment_logout_confirm, (dialog1, which) -> {
                    this.dismiss();
                    RxBus.getInstance().post(new LoginEvent(false));
                })
                .create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        return dialog;
    }
}
