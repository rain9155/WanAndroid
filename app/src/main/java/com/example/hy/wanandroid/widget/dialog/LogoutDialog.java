package com.example.hy.wanandroid.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

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
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setCancelable(false)
                .setIcon(R.drawable.ic_toast)
                .setTitle(R.string.mineFragment_logout_toast)
                .setMessage(R.string.mineFragment_confirm_logout)
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
