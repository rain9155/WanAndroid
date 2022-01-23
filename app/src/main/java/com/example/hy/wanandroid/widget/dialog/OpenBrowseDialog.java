package com.example.hy.wanandroid.widget.dialog;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.utlis.LogUtil;
import com.example.hy.wanandroid.utlis.ShareUtil;

import java.io.File;

import javax.inject.Inject;

/**
 * 打开浏览器弹框
 * Created by 陈健宇 at 2018/12/15
 */
public class OpenBrowseDialog extends BaseDialogFragment {

    private static final String KEY_URL = "url";

    private String mUrl;

    @Inject
    public OpenBrowseDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mUrl = savedInstanceState.getString(KEY_URL);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_URL, mUrl);
    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_open_browse;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            ShareUtil.openBrowser(getContext(), mUrl);
        });
    }

    public void showWithUrl(FragmentManager manager, String tag, String url) {
        mUrl = url;
        show(manager, tag);
    }

}
