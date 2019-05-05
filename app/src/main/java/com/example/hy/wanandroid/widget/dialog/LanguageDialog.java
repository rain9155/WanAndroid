package com.example.hy.wanandroid.widget.dialog;

import android.view.View;
import android.widget.RadioButton;

import com.example.commonlib.utils.LanguageUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.LanguageEvent;

/**
 * 选择语言弹出框
 * Created by 陈健宇 at 2019/5/5
 */
public class LanguageDialog extends BaseDialogFragment {

    private int selectedId = -1;

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_select_language;
    }

    @Override
    protected void initView(View view) {
        if(selectedId != -1) ((RadioButton)view.findViewById(selectedId)).setSelected(true);
        view.findViewById(R.id.rb_lan_system).setOnClickListener(v -> {
            RxBus.getInstance().post(new LanguageEvent(LanguageUtil.SYSTEM));
            this.dismiss();
        });
        view.findViewById(R.id.rb_lan_china).setOnClickListener(v -> {
            RxBus.getInstance().post(new LanguageEvent(LanguageUtil.SIMPLIFIED_CHINESE));
            this.dismiss();
        });
        view.findViewById(R.id.rb_lan_english).setOnClickListener(v -> {
            RxBus.getInstance().post(new LanguageEvent(LanguageUtil.ENGLISH));
            this.dismiss();
        });
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }
}
