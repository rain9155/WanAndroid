package com.example.hy.wanandroid.base.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.UpdataEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by 陈健宇 at 2019/1/1
 */
public abstract class BaseDialogFragment extends DialogFragment {

    protected abstract int getDialogViewId();
    protected abstract void initView(View view);
    private Dialog mDialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(getDialogViewId(), null);
        mDialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(false)
                .create();
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().setWindowAnimations(R.style.DialogAnim);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        initView(view);
        return mDialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        if(this.isAdded())
            this.dismiss();
        else
            super.show(manager, tag);
    }

    /**
     * 禁止按返回键取消dialog
     * 设置点击屏幕Dialog不消失
     */
    protected void CancelBackDismiss() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
    }

}
