package com.example.hy.wanandroid.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.model.prefs.PreferencesHelper;

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
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_NODEMODE, isNight).apply();
    }

    @Override
    public boolean getNightModeState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_NODEMODE, false);
    }

    @Override
    public void setCurrentItem(int position) {
        mPreferences.edit().putInt(Constant.KEY_PREFS_CURRWNTITEM, position).apply();
    }

    @Override
    public int getCurrentItem() {
        return mPreferences.getInt(Constant.KEY_PREFS_CURRWNTITEM, 0);
    }

    @Override
    public void setNoImageState(boolean isNoImage) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_NOIMAGE, isNoImage).apply();
    }

    @Override
    public boolean getNoImageState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_NOIMAGE, false);
    }

    @Override
    public void setAutoCacheState(boolean isAuto) {
        mPreferences.edit().putBoolean(Constant.KEY_PREFS_AUTOCACHE, isAuto).apply();
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_AUTOCACHE, true);
    }

    @Override
    public void setStatusBarState(boolean isStatusBar) {
          mPreferences.edit().putBoolean(Constant.KEY_PREFS_STATUSBAR, isStatusBar).apply();
    }

    @Override
    public boolean getStatusBarState() {
        return mPreferences.getBoolean(Constant.KEY_PREFS_STATUSBAR, true);
    }

    @Override
    public void setDownloadId(long id) {
        mPreferences.edit().putLong(Constant.KEY_DOWNLOAD_ID, id).apply();
    }

    @Override
    public long getDownloadId() {
        return mPreferences.getInt(Constant.KEY_DOWNLOAD_ID, -1);
    }
}
