package com.example.hy.wanandroid.widget.dialog;

import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.ChangeFaceEvent;

/**
 * Created by 陈健宇 at 2019/1/7
 */
public class ChangeFaceDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_change_face;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tv_change_face).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ChangeFaceEvent(true));
        });
        view.findViewById(R.id.tv_change_background).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ChangeFaceEvent(false));
        });
    }
}
