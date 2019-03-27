package com.example.hy.wanandroid.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.UpdataEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * 获取版本信息弹框
 * Created by 陈健宇 at 2018/12/7
 */
public class VersionDialog extends BaseDialogFragment {

    private String mContentText = "";
    private boolean isMain;

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_version_new;
    }

    @Override
    protected void initView(View view) {
        TextView updataLater = view.findViewById(R.id.tv_updata_later);
        TextView updataNow = view.findViewById(R.id.tv_updata_now);
        TextView content = view.findViewById(R.id.tv_version_content);
        content.setText(mContentText);
        updataLater.setOnClickListener(v -> this.dismiss());
        updataNow.setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new UpdataEvent(isMain));
        });
        CancelBackDismiss();
    }

    public void setContentText(String content){
        mContentText = content;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }
}
