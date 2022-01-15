package com.example.hy.wanandroid.widget.dialog;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.ClearCacheEvent;

import javax.inject.Inject;

/**
 * 清除缓存弹框
 * Created by 陈健宇 at 2018/12/15
 */
public class ClearCacheDialog extends BaseDialogFragment {

    private String mContent = "";

    @Inject
    public ClearCacheDialog() {

    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_clear_cache;
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void initView(View view) {
        ((TextView)view.findViewById(R.id.tv_clear)).setText(getString(R.string.dialog_clear_cache1) + mContent + getString(R.string.dialog_clear_cache2));
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ClearCacheEvent());
        });
    }

    public void setContent(String content) {
        this.mContent = content;
    }

}
