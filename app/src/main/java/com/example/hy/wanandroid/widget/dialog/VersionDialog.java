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
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.UpdataEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by 陈健宇 at 2018/12/7
 */
public class VersionDialog extends DialogFragment {

    private String mContentText = "";
    private boolean isMain;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_version_new, null);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(false)
                .create();
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.setOnKeyListener((dialog1, keyCode, event) -> keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0);
        Button updataLater = view.findViewById(R.id.btn_updata_later);
        Button updataNow = view.findViewById(R.id.btn_updata_now);
        TextView content = view.findViewById(R.id.tv_version_content);
        content.setText(mContentText);
        updataLater.setOnClickListener(v -> dialog.dismiss());
        updataNow.setOnClickListener(v -> {
            dialog.dismiss();
            RxBus.getInstance().post(new UpdataEvent(isMain));
        });
        return dialog;
    }

    public void setContentText(String content){
        mContentText = content;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }
}
