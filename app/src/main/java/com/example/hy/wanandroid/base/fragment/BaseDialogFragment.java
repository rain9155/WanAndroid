package com.example.hy.wanandroid.base.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.hy.wanandroid.R;

import java.lang.reflect.Field;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Created by 陈健宇 at 2019/1/1
 */
public abstract class BaseDialogFragment extends DialogFragment {

    private Dialog mDialog;

    protected abstract int getDialogViewId();

    protected int getGravity() {
        return Gravity.CENTER;
    }

    protected int getDialogAnimStyle() {
        return R.style.DialogAnim;
    }

    protected int getDialogTheme() {
        return R.style.DialogTheme;
    }

    protected ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 是否禁止按返回键取消dialog/点击屏幕Dialog不消失
     */
    protected boolean isCancelBackDismiss() {
        return false;
    }

    protected void initView(View view) {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(getDialogViewId(), null);
        mDialog = new AlertDialog.Builder(getActivity(), getDialogTheme())
                .setView(view)
                .create();
        Window window = mDialog.getWindow();
        if(window != null) {
            window.setWindowAnimations(getDialogAnimStyle());
            window.setGravity(getGravity());
            window.getDecorView().setPadding(0, 0, 0, 0);
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            window.getAttributes().width = layoutParams.width;
            window.getAttributes().height = layoutParams.height;
        }
        if(isCancelBackDismiss()) {
            mDialog.setCancelable(false);
            mDialog.setCanceledOnTouchOutside(false);
            mDialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        }
        initView(view);
        return mDialog;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        //防止重复添加
        if(this.isAdded()) {
            this.dismiss();
        }
        //防止Activity将要销毁时不能添加而导致报错
        try {
            Class dialogFragmentClass = this.getClass();
            Field f1 = dialogFragmentClass.getDeclaredField("mDismissed");
            Field f2 = dialogFragmentClass.getDeclaredField("mShownByMe");
            f1.setAccessible(true);
            f2.setAccessible(true);
            f1.setBoolean(this, false);
            f2.setBoolean(this, true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitNowAllowingStateLoss();

    }
}
