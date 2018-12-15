package com.example.hy.wanandroid.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.ClearCacheEvent;
import com.example.hy.wanandroid.utils.LogUtil;
import com.example.hy.wanandroid.utils.ShareUtil;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

/**
 * Created by 陈健宇 at 2018/12/15
 */
public class OpenBrowseDialog extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_open_browse, null);
        AlertDialog dialog =  new AlertDialog.Builder(getActivity())
                .setView(view)
                .setCancelable(false)
                .create();
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            LogUtil.d(LogUtil.TAG_COMMON, "DownloadManager不可用");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "app-release.apk");
            if(file.exists()) file.delete();
            ShareUtil.openBrowser(getContext(), Constant.NEW_VERSION_URL);
        });
        return dialog;
    }
}
