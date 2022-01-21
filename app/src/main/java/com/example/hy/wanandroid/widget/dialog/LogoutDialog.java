package com.example.hy.wanandroid.widget.dialog;

import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.LoginEvent;

import javax.inject.Inject;

/**
 * 退出登陆弹框
 * Created by 陈健宇 at 2018/12/11
 */
public class LogoutDialog extends BaseDialogFragment {

    @Inject
    public LogoutDialog() {

    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_logout;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.btn_cancel).setOnClickListener(v ->
                this.dismiss()
        );
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new LoginEvent(false));
        });
        cancelBackDismiss();
    }
}
