package com.example.hy.wanandroid.widget.dialog;

import android.view.View;

import com.example.hy.wanandroid.utlis.ShareUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.App;

/**
 * 前往应用信息弹框
 * Created by 陈健宇 at 2019/3/25
 */
public class GotoDetialDialog extends BaseDialogFragment {
    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_goto_detail;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            ShareUtil.gotoAppDetailIntent(App.getContext());
        });
    }
}
