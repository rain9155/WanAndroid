package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * 设置的Contract
 * Created by 陈健宇 at 2018/11/26
 */
public interface SettingsContract {

    interface View extends BaseView {

    }

    interface Presenter extends IPresenter<View>{
        void setNoImageState(boolean isNight);
        void setAutoCacheState(boolean isNight);
        void setNightModeState(boolean isNight);
        void setStatusBarState(boolean isStatusBar);
    }
}
