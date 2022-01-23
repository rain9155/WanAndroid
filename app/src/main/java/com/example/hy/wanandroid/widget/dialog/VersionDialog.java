package com.example.hy.wanandroid.widget.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.entity.Apk;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.UpdateEvent;

import javax.inject.Inject;

/**
 * 获取版本信息弹框
 * Created by 陈健宇 at 2018/12/7
 */
public class VersionDialog extends BaseDialogFragment {

    private static final String KEY_APK = "apk";

    private Apk mApk;

    @Inject
    public VersionDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mApk = (Apk) savedInstanceState.getSerializable(KEY_APK);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_APK, mApk);
    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_version_new;
    }

    @Override
    protected boolean isCancelBackDismiss() {
        return true;
    }

    @Override
    protected void initView(View view) {
        StringBuilder contentBuilder = new StringBuilder();
        contentBuilder.append(getString(R.string.dialog_versionName)).append(mApk.getVersionName()).append("\n")
                .append(getString(R.string.dialog_versionSize)).append(mApk.getSize()).append("\n")
                .append(getString(R.string.dialog_versionContent)).append("\n").append(mApk.getVersionBody());
        Button btnUpdateLater = view.findViewById(R.id.btn_update_later);
        Button btnUpdateNow = view.findViewById(R.id.btn_update_now);
        TextView tvVersionContent = view.findViewById(R.id.tv_version_content);
        tvVersionContent.setText(contentBuilder.toString());
        btnUpdateLater.setOnClickListener(v -> this.dismiss());
        btnUpdateNow.setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new UpdateEvent(mApk));
        });
    }

    public void showWithApkInfo(FragmentManager manager, String tag, Apk apk) {
        mApk = apk;
        show(manager, tag);
    }
}
