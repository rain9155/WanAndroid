package com.example.hy.wanandroid.widget.dialog;

import android.view.View;
import android.widget.RadioButton;

import com.example.hy.wanandroid.utlis.LanguageUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.LanguageEvent;

/**
 * 选择语言弹出框
 * Created by 陈健宇 at 2019/5/5
 */
public class LanguageDialog extends BaseDialogFragment {


    private String mSelectedLanguage;

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_select_language;
    }

    @Override
    protected void initView(View view) {

        int selectedId = getLanguageSelectedId(App.getContext().getAppComponent().getDataModel().getSelectedLanguage());
        if(selectedId != -1) ((RadioButton)view.findViewById(selectedId)).setChecked(true);

        view.findViewById(R.id.rb_lan_system).setOnClickListener(v -> mSelectedLanguage = LanguageUtil.SYSTEM);
        view.findViewById(R.id.rb_lan_china).setOnClickListener(v -> mSelectedLanguage = LanguageUtil.SIMPLIFIED_CHINESE);
        view.findViewById(R.id.rb_lan_english).setOnClickListener(v -> mSelectedLanguage = LanguageUtil.ENGLISH);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            if(mSelectedLanguage != null){
                if(!mSelectedLanguage.equals(App.getContext().getAppComponent().getDataModel().getSelectedLanguage()))
                    RxBus.getInstance().post(new LanguageEvent(mSelectedLanguage));
            }
            this.dismiss();
        });
    }

    /**
     * 获取当前的语言设置对应的单选控件按钮id
     * @return
     */
    private int getLanguageSelectedId(String lan) {
        int ret = -1;
        switch (lan){
            case LanguageUtil.SYSTEM:
                ret = R.id.rb_lan_system;
                break;
            case LanguageUtil.SIMPLIFIED_CHINESE:
                ret = R.id.rb_lan_china;
                break;
            case LanguageUtil.ENGLISH:
                ret = R.id.rb_lan_english;
                break;
            default:
                break;
        }
        return ret;
    }

}
