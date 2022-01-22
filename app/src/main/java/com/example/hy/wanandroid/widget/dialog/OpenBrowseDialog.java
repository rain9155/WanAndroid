package com.example.hy.wanandroid.widget.dialog;

import android.os.Environment;
import android.view.View;

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

    @Inject
    public OpenBrowseDialog() {

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
            LogUtil.d(LogUtil.TAG_COMMON, "DownloadManager不可用");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "app-release.apk");
            if(file.exists()) {
                file.delete();
            }
            ShareUtil.openBrowser(getContext(), Constant.NEW_VERSION_URL);
        });
    }

}
