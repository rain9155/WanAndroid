package com.example.hy.wanandroid.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.ClearCacheEvent;
import com.example.hy.wanandroid.event.LoginEvent;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

/**
 * 清除缓存弹框
 * Created by 陈健宇 at 2018/12/15
 */
public class ClearCacheDialog extends BaseDialogFragment {

    private String mContent = "";
    private Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_clear_cache;
    }

    @Override
    protected void initView(View view) {
        ((TextView)view.findViewById(R.id.tv_clear)).setText(mContent);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ClearCacheEvent());
        });
    }

    public void setContent(String content) {
        mContent = mContext.getString(R.string.dialog_clear_cache1) + content + mContext.getString(R.string.dialog_clear_cache2);
    }

}
