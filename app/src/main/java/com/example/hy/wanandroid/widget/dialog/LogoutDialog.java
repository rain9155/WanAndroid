package com.example.hy.wanandroid.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.LoginEvent;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

/**
 * Created by 陈健宇 at 2018/12/11
 */
public class LogoutDialog extends DialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog dialog;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog = new AlertDialog.Builder(getActivity(), R.style.AlertDialog)
                    .setCancelable(false)
                    .setIcon(R.drawable.ic_toast)
                    .setTitle(R.string.dialog_logout_toast)
                    .setMessage(R.string.dialog_confirm_logout)
                    .setNegativeButton(R.string.dialog_logout_cancel, (dialog1, which) -> this.dismiss())
                    .setPositiveButton(R.string.dialog_logout_confirm, (dialog1, which) -> {
                        this.dismiss();
                        RxBus.getInstance().post(new LoginEvent(false));
                    })
                    .create();
            dialog.show();
            try {
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);
                //通过反射修改title字体大小和颜色
                Field mTitle = mAlertController.getClass().getDeclaredField("mTitleView");
                mTitle.setAccessible(true);
                TextView mTitleView = (TextView) mTitle.get(mAlertController);
                mTitleView.setTextSize(20);
                mTitleView.setTextColor(getResources().getColor(R.color.primaryText));
                //通过反射修改message字体大小和颜色
                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                mMessage.setAccessible(true);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                mMessageView.setTextSize(18);
                mMessageView.setPadding(80, 50, 0, 0);
                mMessageView.setTextColor(getResources().getColor(R.color.primaryText));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.primaryText));
            dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.primaryText));
        }else {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_logout, null);
            dialog = new AlertDialog.Builder(getActivity())
                    .setCancelable(false)
                    .setView(view)
                    .create();
            view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
            view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
                this.dismiss();
                RxBus.getInstance().post(new LoginEvent(false));
            });
        }
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        return dialog;
    }
}
