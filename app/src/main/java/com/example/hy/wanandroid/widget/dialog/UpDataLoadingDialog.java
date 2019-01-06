package com.example.hy.wanandroid.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 获取版本等待loading
 * Created by 陈健宇 at 2018/12/7
 */
public class UpDataLoadingDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_version_loading;
    }

    @Override
    protected void initView(View view) {
        CancelBackDismiss();
    }

}
