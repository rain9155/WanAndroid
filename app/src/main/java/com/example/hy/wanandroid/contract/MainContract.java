package com.example.hy.wanandroid.contract;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Apk;

/**
 * Mian活动
 * Created by 陈健宇 at 2018/10/23
 */
public interface MainContract {

    interface View extends IView {
        void showUpdateDialog(Apk apk);//显示更新弹窗
        void updateVersion(Apk apk);//更新应用
        void showOpenBrowseDialog(String url);//前往系统浏览器
        void installApk(String uri);//安装应用
    }

    interface Presenter{
        void setCurrentItem(int position);
        int getCurrentItem();
        boolean getAutoUpdateState();
        void checkVersion(String currentVersion);

    }
}
