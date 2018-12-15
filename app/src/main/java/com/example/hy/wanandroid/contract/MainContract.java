package com.example.hy.wanandroid.contract;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * Mian活动
 * Created by 陈健宇 at 2018/10/23
 */
public interface MainContract {

    interface View extends BaseView {
        void showUpdataDialog(String content);//显示更新弹窗
        void setNewVersionName(String versionName);//设置最新版本号
        void upDataVersion();//更新
        void showOpenBrowseDialog();
    }

    interface Presenter extends IPresenter<MainContract.View>{
        void setCurrentItem(int position);
        int getCurrentItem();
        void checkVersion(String currentVersion);
    }
}
