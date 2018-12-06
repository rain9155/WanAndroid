package com.example.hy.wanandroid.model;

import com.example.hy.wanandroid.model.db.DbHelper;
import com.example.hy.wanandroid.model.db.DbHelperImp;
import com.example.hy.wanandroid.model.network.NetworkHelper;
import com.example.hy.wanandroid.model.network.NetworkHelperImp;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.FirstHierarchy;
import com.example.hy.wanandroid.model.network.entity.SecondHierarchy;
import com.example.hy.wanandroid.model.network.entity.Articles;
import com.example.hy.wanandroid.model.network.entity.BannerData;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.model.network.entity.CollectionRequest;
import com.example.hy.wanandroid.model.network.entity.Login;
import com.example.hy.wanandroid.model.network.entity.Tag;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.model.network.entity.HotKey;
import com.example.hy.wanandroid.model.prefs.PreferencesHelper;
import com.example.hy.wanandroid.model.prefs.PreferencesHelperImp;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/11/26
 */
public class DataModel implements NetworkHelper, DbHelper, PreferencesHelper{

    private NetworkHelper mNetworkHelper;
    private DbHelper mDbHelper;
    private PreferencesHelper mPreferencesHelper;

    @Inject
    public DataModel(NetworkHelperImp networkHelper, DbHelperImp dbHelper, PreferencesHelperImp preferencesHelper) {
        mNetworkHelper = networkHelper;
        mDbHelper = dbHelper;
        mPreferencesHelper = preferencesHelper;
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerDatas() {
        return mNetworkHelper.getBannerDatas();
    }

    @Override
    public Observable<BaseResponse<Articles>> getArticles(int pageNum) {
        return mNetworkHelper.getArticles(pageNum);
    }

    @Override
    public Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList() {
        return mNetworkHelper.getFirstHierarchyList();
    }

    @Override
    public Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id) {
        return mNetworkHelper.getArticles(pageNum, id);
    }

    @Override
    public Observable<BaseResponse<List<Tab>>> getProjectList() {
        return mNetworkHelper.getProjectList();
    }

    @Override
    public Observable<BaseResponse<Articles>> getProjects(int pageNum, int id) {
        return mNetworkHelper.getProjects(pageNum, id);
    }

    @Override
    public Observable<BaseResponse<Login>> getLoginRequest(String username, String password) {
        return mNetworkHelper.getLoginRequest(username, password);
    }

    @Override
    public Observable<BaseResponse<Login>> getLogoutRequest() {
        return mNetworkHelper.getLogoutRequest();
    }

    @Override
    public Observable<BaseResponse<Login>> getRegisterRequest(String username, String password, String rePassword) {
        return mNetworkHelper.getRegisterRequest(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<CollectionRequest>> getCollectionRequest(int pageNum) {
        return mNetworkHelper.getCollectionRequest(pageNum);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id, int originalId) {
        return mNetworkHelper.getUnCollectRequest(id, originalId);
    }

    @Override
    public Observable<BaseResponse<Articles>> getSearchResquest(String key, int pageNum) {
        return mNetworkHelper.getSearchResquest(key, pageNum);
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return mNetworkHelper.getHotKey();
    }

    @Override
    public Observable<BaseResponse<List<Tag>>> getTags() {
        return mNetworkHelper.getTags();
    }

    @Override
    public Observable<BaseResponse<Collection>> getCollectRequest(int id) {
        return mNetworkHelper.getCollectRequest(id);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id) {
        return mNetworkHelper.getUnCollectRequest(id);
    }

    @Override
    public boolean addHistoryRecord(String record) {
        return mDbHelper.addHistoryRecord(record);
    }

    @Override
    public int deleteOneHistoryRecord(String record) {
        return  mDbHelper.deleteOneHistoryRecord(record);
    }

    @Override
    public int deleteAllHistoryRecord() {
        return mDbHelper.deleteAllHistoryRecord();
    }

    @Override
    public boolean isExistHistoryRecord(String record) {
        return mDbHelper.isExistHistoryRecord(record);
    }

    @Override
    public List<String> getAllHistoryRecord() {
        return mDbHelper.getAllHistoryRecord();
    }

    @Override
    public void setNightModeState(boolean isNight) {
        mPreferencesHelper.setNightModeState(isNight);
    }

    @Override
    public boolean getNightModeState() {
        return mPreferencesHelper.getNightModeState();
    }

    @Override
    public void setCurrentItem(int position) {
        mPreferencesHelper.setCurrentItem(position);
    }

    @Override
    public int getCurrentItem() {
        return mPreferencesHelper.getCurrentItem();
    }

    @Override
    public void setNoImageState(boolean isNoImage) {
        mPreferencesHelper.setNoImageState(isNoImage);
    }

    @Override
    public boolean getNoImageState() {
        return mPreferencesHelper.getNoImageState();
    }

    @Override
    public void setAutoCacheState(boolean isAuto) {
        mPreferencesHelper.setAutoCacheState(isAuto);
    }

    @Override
    public boolean getAutoCacheState() {
        return mPreferencesHelper.getAutoCacheState();
    }

    @Override
    public void setStatusBarState(boolean isStatusBar) {
        mPreferencesHelper.setStatusBarState(isStatusBar);
    }

    @Override
    public boolean getStatusBarState() {
        return mPreferencesHelper.getStatusBarState();
    }
}
