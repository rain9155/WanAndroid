package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Apk;

/**
 * 设置的Contract
 * Created by 陈健宇 at 2018/11/26
 */
public interface SettingsContract {

    interface View extends IView {
        void showUpdateDialog(Apk apk);//显示更新弹窗
        void handleLanguageChange();//处理更换语言事件
        void handleThemeChange();//处理主题更换事件
        void clearCache();//清空缓存
    }

    interface Presenter {
        void setNoImageState(boolean isNight);
        void setAutoCacheState(boolean isNight);
        void setStatusBarState(boolean isStatusBar);
        void setAutoUpdateState(boolean isAutoUpdate);
        void checkVersion(String currentVersion);
        boolean getNoImageState();
        boolean getAutoCacheState();
        boolean getStatusBarState();
        boolean getAutoUpdateState();
        String getSelectedLanguage();
        String getSelectedTheme();
    }
}
