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
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.LoginEvent;

import java.lang.reflect.Field;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by 陈健宇 at 2018/12/11
 */
public class LogoutDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_logout;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new LoginEvent(false));
        });
        CancelBackDismiss();
    }
}
