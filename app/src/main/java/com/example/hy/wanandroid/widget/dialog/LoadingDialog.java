package com.example.hy.wanandroid.widget.dialog;

import android.view.View;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;

/**
 * 加载框
 * Created by 陈健宇 at 2018/11/17
 */
public class LoadingDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initView(View view) {
        CancelBackDismiss();
    }

}
