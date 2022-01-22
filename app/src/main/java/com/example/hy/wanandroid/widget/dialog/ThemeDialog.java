package com.example.hy.wanandroid.widget.dialog;

import android.view.View;
import android.widget.RadioButton;

import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseDialogFragment;
import com.example.hy.wanandroid.event.LanguageEvent;
import com.example.hy.wanandroid.event.ThemeEvent;
import com.example.hy.wanandroid.utlis.LanguageUtil;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.ThemeUtil;

import javax.inject.Inject;

/**
 * @author chenjianyu
 * @date 1/22/22
 */
public class ThemeDialog extends BaseDialogFragment {


    private String mSelectedTheme;

    @Inject
    public ThemeDialog() {

    }

    @Override
    protected int getDialogViewId() {
        return R.layout.dialog_select_theme;
    }

    @Override
    protected void initView(View view) {
        String selectedTheme = App.getContext().getAppComponent().getDataModel().getSelectedTheme();
        int selectedId = getThemeSelectedId(selectedTheme);
        if(selectedId != -1) {
            ((RadioButton)view.findViewById(selectedId)).setChecked(true);
        }
        view.findViewById(R.id.rb_theme_system).setOnClickListener(v -> mSelectedTheme = ThemeUtil.SYSTEM);
        view.findViewById(R.id.rb_theme_dark).setOnClickListener(v -> mSelectedTheme = ThemeUtil.DARK);
        view.findViewById(R.id.rb_theme_light).setOnClickListener(v -> mSelectedTheme = ThemeUtil.LIGHT);
        view.findViewById(R.id.btn_cancel).setOnClickListener(v -> this.dismiss());
        view.findViewById(R.id.btn_confirm).setOnClickListener(v -> {
            this.dismiss();
            if(mSelectedTheme != null && !mSelectedTheme.equals(selectedTheme)){
                RxBus.getInstance().post(new ThemeEvent(mSelectedTheme));
            }
        });
    }

    private int getThemeSelectedId(String theme) {
        int ret;
        switch (theme){
            case ThemeUtil.DARK:
                ret = R.id.rb_theme_dark;
                break;
            case ThemeUtil.LIGHT:
                ret = R.id.rb_theme_light;
                break;
            default:
                ret = R.id.rb_theme_system;
                break;
        }
        return ret;
    }


}
