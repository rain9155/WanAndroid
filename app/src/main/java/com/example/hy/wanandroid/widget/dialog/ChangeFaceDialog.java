package com.example.hy.wanandroid.widget.dialog;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.hy.wanandroid.utlis.FileUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.ChangeFaceEvent;

import javax.inject.Inject;

/**
 * 改变背景弹框
 * Created by 陈健宇 at 2019/1/7
 */
public class ChangeFaceDialog extends BaseDialogFragment {

    @Inject
    public ChangeFaceDialog() {

    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_change_face;
    }

    @Override
    protected int getDialogAnimStyle() {
        return R.style.DialogBottomAnim;
    }

    @Override
    protected int getGravity() {
        return Gravity.BOTTOM;
    }

    @Override
    protected ViewGroup.LayoutParams getLayoutParams() {
        return new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView(View view) {
        boolean changeDefaultImage = FileUtil.isFileExist(Constant.PATH_IMAGE_FACE, Constant.FACE) || FileUtil.isFileExist(Constant.PATH_IMAGE_BACKGROUND, Constant.BACK);
        view.findViewById(R.id.divider_2).setVisibility(changeDefaultImage ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.tv_change_moren).setVisibility(changeDefaultImage ? View.VISIBLE : View.GONE);
        view.findViewById(R.id.tv_change_face).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ChangeFaceEvent(Constant.CHANGE_FACE));
        });
        view.findViewById(R.id.tv_change_background).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ChangeFaceEvent(Constant.CHANGE_BACK));
        });
        view.findViewById(R.id.tv_change_moren).setOnClickListener(v -> {
            this.dismiss();
            RxBus.getInstance().post(new ChangeFaceEvent(Constant.CHANGE_NO));
        });
    }
}
