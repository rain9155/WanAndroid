package com.example.hy.wanandroid.widget.dialog;

import android.view.View;

import com.example.hy.wanandroid.utlis.FileUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.ChangeFaceEvent;

/**
 * 改变背景弹框
 * Created by 陈健宇 at 2019/1/7
 */
public class ChangeFaceDialog extends BaseDialogFragment {

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_change_face;
    }

    @Override
    protected void initView(View view) {
        view.findViewById(R.id.tv_change_moren).setVisibility(
                (
                        FileUtil.loadBitmap(Constant.PATH_IMAGE_FACE, Constant.FACE) != null || FileUtil.loadBitmap(Constant.PATH_IMAGE_BACKGROUND, Constant.BACK) != null)
                        ? View.VISIBLE : View.GONE
        );
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
