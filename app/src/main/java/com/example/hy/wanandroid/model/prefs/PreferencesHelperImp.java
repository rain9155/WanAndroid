package com.example.hy.wanandroid.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.commonlib.utils.LanguageUtil;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;

import javax.inject.Inject;

/**
 * SharedPreferences
 * Created by 陈健宇 at 2018/11/26
 */
public class PreferencesHelperImp implements PreferencesHelper {

    private SharedPreferences mPreferences;

    @Inject
    public PreferencesHelperImp() {
        mPreferences = App.getContext().getSharedPreferences(Constant.SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void setNightModeState(boolean isNight) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_NODE_MODE, isNight).apply();
    }

    @Override
    public boolean getNightModeState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_NODE_MODE, false);
    }

    @Override
    public void setCurMainItem(int position) {
        mPreferences.edit().putInt(Constant.KEY_PREFS_CUR_MAIN_ITEM, position).apply();
    }

    @Override
    public void setCurWechatItem(int position) {
        mPreferences.edit().putInt(Constant.KEY_PREFS_CUR_WECHAT_ITEM, position).apply();
    }

    @Override
    public void setCurProjectItem(int position) {
        mPreferences.edit().putInt(Constant.KEY_PREFS_CUR_PROJECT_ITEM, position).apply();
    }

    @Override
    public int getCurMainItem() {
        return mPreferences.getInt(Constant.KEY_PREFS_CUR_MAIN_ITEM, 0);
    }

    @Override
    public int getCurWechatItem() {
        return mPreferences.getInt(Constant.KEY_PREFS_CUR_WECHAT_ITEM, 0);
    }

    @Override
    public int getCurProjectItem() {
        return mPreferences.getInt(Constant.KEY_PREFS_CUR_PROJECT_ITEM, 0);
    }

    @Override
    public void setNoImageState(boolean isNoImage) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_NO_IMAGE, isNoImage).apply();
    }

    @Override
    public boolean getNoImageState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_NO_IMAGE, false);
    }

    @Override
    public void setAutoCacheState(boolean isAuto) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_AUTO_CACHE, isAuto).apply();
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_AUTO_CACHE, true);
    }

    @Override
    public void setStatusBarState(boolean isStatusBar) {
          mPreferences.edit().putBoolean(Constant.KEY_PREFS_STATUS_BAR, isStatusBar).apply();
    }

    @Override
    public boolean getStatusBarState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_STATUS_BAR, true);
    }

    @Override
    public void setDownloadId(long id) {
        mPreferences.edit().putLong(Constant.KEY_DOWNLOAD_ID, id).apply();
    }

    @Override
    public long getDownloadId() {
        return mPreferences.getLong(Constant.KEY_DOWNLOAD_ID, -1L);
    }

    @Override
    public void setNetWorkState(boolean isConnection) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_NETWORK, isConnection).apply();
    }

    @Override
    public boolean getNetWorkState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_NETWORK, true);
    }

    @Override
    public void setAutoUpdataState(boolean isAuto) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_AUTO_UPDATA, isAuto).apply();
    }

    @Override
    public boolean getAutoUpdataState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_AUTO_UPDATA, true);
    }

    @Override
    public String getSelectedLanguage() {
        return mPreferences.getString(Constant.KEY_PREFS_LAN, LanguageUtil.SYSTEM);
    }

    @Override
    public void setSelectedLanguage(String lan) {
        mPreferences.edit().putString(Constant.KEY_PREFS_LAN, lan).apply();
    }


}
