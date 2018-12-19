package com.example.hy.wanandroid.model.network;

import com.example.hy.wanandroid.model.network.api.HierarchyApis;
import com.example.hy.wanandroid.model.network.api.HomeApis;
import com.example.hy.wanandroid.model.network.api.MineApis;
import com.example.hy.wanandroid.model.network.api.NavigationApis;
import com.example.hy.wanandroid.model.network.api.ProjectApis;
import com.example.hy.wanandroid.model.network.api.SearchApis;
import com.example.hy.wanandroid.model.network.api.VersionApi;
import com.example.hy.wanandroid.model.network.api.WechatApis;
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
import com.example.hy.wanandroid.model.network.entity.Version;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 网络操作
 * Created by 陈健宇 at 2018/11/26
 */
public class NetworkHelperImp implements NetworkHelper{

    private HomeApis mHomeApis;
    private MineApis mMineApis;
    private NavigationApis mNavigationApis;
    private ProjectApis mProjectApis;
    private SearchApis mSearchApis;
    private HierarchyApis mHierarchyApis;
    private VersionApi mVersionApi;
    private WechatApis mWechatApis;
    
    @Inject
    public NetworkHelperImp(WechatApis wechatApis, VersionApi versionApi, HomeApis homeApis, MineApis mineApis, NavigationApis navigationApis, ProjectApis projectApis, SearchApis searchApis, HierarchyApis hierarchyApis) {
        mHomeApis = homeApis;
        mMineApis = mineApis;
        mNavigationApis = navigationApis;
        mProjectApis = projectApis;
        mSearchApis = searchApis;
        mHierarchyApis = hierarchyApis;
        mVersionApi = versionApi;
        mWechatApis = wechatApis;
    }

    @Override
    public Observable<BaseResponse<List<BannerData>>> getBannerDatas() {
        return mHomeApis.getBannerDatas();
    }

    @Override
    public Observable<BaseResponse<Articles>> getArticles(int pageNum) {
        return mHomeApis.getArticles(pageNum);
    }

    @Override
    public Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList() {
        return mHierarchyApis.getFirstHierarchyList();
    }

    @Override
    public Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id) {
        return mHierarchyApis.getSecondHierarchyList(pageNum, id);
    }

    @Override
    public Observable<BaseResponse<List<Tab>>> getProjectList() {
        return mProjectApis.getProjectList();
    }

    @Override
    public Observable<BaseResponse<Articles>> getProjects(int pageNum, int id) {
        return mProjectApis.getProjects(pageNum, id);
    }

    @Override
    public Observable<BaseResponse<List<Tab>>> getWeChatTabs() {
        return mWechatApis.getWechatTabs();
    }

    @Override
    public Observable<BaseResponse<Articles>> getWeChats(int id, int pageNum) {
        return mWechatApis.getWechatArticles(id, pageNum);
    }

    @Override
    public Observable<BaseResponse<Login>> getLoginRequest(String username, String password) {
        return mMineApis.getLoginRequest(username, password);
    }

    @Override
    public Observable<BaseResponse<Login>> getLogoutRequest() {
        return mMineApis.getLogoutRequest();
    }

    @Override
    public Observable<BaseResponse<Login>> getRegisterRequest(String username, String password, String rePassword) {
        return mMineApis.getRegisterRequest(username, password, rePassword);
    }

    @Override
    public Observable<BaseResponse<CollectionRequest>> getCollectionRequest(int pageNum) {
        return mMineApis.getCollectionRequest(pageNum);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id, int originalId) {
        return mMineApis.getUnCollectionRequest(id, originalId);
    }

    @Override
    public Observable<BaseResponse<Articles>> getSearchResquest(String key, int pageNum) {
        return mSearchApis.getSearchRequest(key, pageNum);
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return mSearchApis.getHotKey();
    }

    @Override
    public Observable<BaseResponse<List<Tag>>> getTags() {
        return mNavigationApis.getTags();
    }

    @Override
    public Observable<Version> getVersionDetails() {
        return mVersionApi.getVersionDetial();
    }

    @Override
    public Observable<BaseResponse<Collection>> getCollectRequest(int id) {
        return mHomeApis.getCollectRequest(id);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id) {
        return mHomeApis.getUnCollectRequest(id);
    }
}
