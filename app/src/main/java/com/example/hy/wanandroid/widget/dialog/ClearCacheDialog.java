package com.example.hy.wanandroid.widget.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

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

    private static final String KEY_CACHE = "cache";

    private String mCache;

    @Inject
    public ClearCacheDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mCache = savedInstanceState.getString(KEY_CACHE);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_CACHE, mCache);
    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_clear_cache;
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void initView(View view) {
        ((TextView)view.findViewById(R.id.tv_clear)).setText(getString(R.string.dialog_clear_cache1) + mCache + getString(R.string.dialog_clear_cache2));
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ClearCacheEvent());
        });
    }

    public void showWithCache(FragmentManager manager, String tag, String cache) {
        mCache = cache;
        show(manager, tag);
    }

}
