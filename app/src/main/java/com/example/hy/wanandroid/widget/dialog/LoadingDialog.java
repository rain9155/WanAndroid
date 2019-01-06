package com.example.hy.wanandroid.widget.dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
 * 加载框
 * Created by 陈健宇 at 2018/11/17
 */
public class LoadingDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initView(View view) {
        CancelBackDismiss();
    }

}
