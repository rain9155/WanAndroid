package com.example.hy.wanandroid.widget.dialog;

import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.UpdataEvent;

import javax.inject.Inject;

/**
 * 获取版本信息弹框
 * Created by 陈健宇 at 2018/12/7
 */
public class VersionDialog extends BaseDialogFragment {

    private String mContentText = "";
    private boolean isMain;

    @Inject
    public VersionDialog() {

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
        TextView updataLater = view.findViewById(R.id.tv_updata_later);
        TextView updataNow = view.findViewById(R.id.tv_updata_now);
        TextView content = view.findViewById(R.id.tv_version_content);
        content.setText(mContentText);
        updataLater.setOnClickListener(v -> this.dismiss());
        updataNow.setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new UpdataEvent(isMain));
        });
    }

    public void setContentText(String content){
        mContentText = content;
    }

    public void setIsMain(boolean isMain) {
        this.isMain = isMain;
    }
}
