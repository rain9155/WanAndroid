package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;

/**
 * 设置的Contract
 * Created by 陈健宇 at 2018/11/26
 */
public interface SettingsContract {

    interface View extends IView {
        void showUpdataDialog(String content);//显示更新弹窗
        void setNewVersionName(String versionName);//设置最新版本号
        void showAlreadyNewToast(String content);//已经是最新版本
        void handleLanguage();//处理更换语言事件
        void clearCache();//清空缓存
        void upDataVersion();//更新
    }

    interface Presenter {
        void setNoImageState(boolean isNight);
        void setAutoCacheState(boolean isNight);
        void setNightModeState(boolean isNight);
        void setStatusBarState(boolean isStatusBar);
        boolean getNoImageState();
        boolean getAutoCacheState();
        boolean getNightModeState();
        boolean getStatusBarState();
        boolean getAutoUpdataState();
        String getSelectedLanguage();
        void checkVersion(String currentVersion);
    }
}
