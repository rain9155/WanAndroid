package com.example.hy.wanandroid.model.network;

import com.example.hy.wanandroid.entity.BaseResponse;
import com.example.hy.wanandroid.entity.CoinRanks;
import com.example.hy.wanandroid.entity.Coins;
import com.example.hy.wanandroid.entity.FirstHierarchy;
import com.example.hy.wanandroid.entity.SecondHierarchy;
import com.example.hy.wanandroid.entity.Articles;
import com.example.hy.wanandroid.entity.BannerData;
import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.entity.CollectionRequest;
import com.example.hy.wanandroid.entity.Login;
import com.example.hy.wanandroid.entity.Tag;
import com.example.hy.wanandroid.entity.Tab;
import com.example.hy.wanandroid.entity.HotKey;
import com.example.hy.wanandroid.entity.UserCoin;
import com.example.hy.wanandroid.entity.Version;

import java.util.List;

import io.reactivex.Observable;

/**
 * 网络操作接口
 * Created by 陈健宇 at 2018/11/26
 */
public interface NetworkHelper {

    /** homepager */
    //获得banner数据
    Observable<BaseResponse<List<BannerData>>> getBannerDatas();
    //获得首页文章数据
    Observable<BaseResponse<Articles>> getArticles(int pageNum);

    /** hierarchy */
    //获得一级体系
    Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList();
    //获得二级体系文章
    Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id);

    /** project */
    //获得项目列表名
    Observable<BaseResponse<List<Tab>>> getProjectList();
    //获得项目列表
    Observable<BaseResponse<Articles>> getProjects(int pageNum, int id);

    /** wechats */
    //获得wechat的tabs
    Observable<BaseResponse<List<Tab>>> getWeChatTabs();
    //获得wechat列表
    Observable<BaseResponse<Articles>> getWeChats(int pageNum, int id);

    /** mine */
    //获得登陆结果
    Observable<BaseResponse<Login>> getLoginRequest(String username, String password);
    //获得退出登陆结果
    Observable<BaseResponse<Login>> getLogoutRequest();
    //获得注册结果
    Observable<BaseResponse<Login>> getRegisterRequest(String username, String password, String rePassword);

    //获得收藏结果
    Observable<BaseResponse<CollectionRequest>> getCollectionRequest(int pageNum);
    //取消收藏
    Observable<BaseResponse<Collection>> getUnCollectRequest(int id, int originalId);

    /** search */
    //获得搜索结果
    Observable<BaseResponse<Articles>> getSearchResquest(String key, int pageNum);
    //获得热词列表
    Observable<BaseResponse<List<HotKey>>> getHotKey();

    /** navigation */
    //获得标签
    Observable<BaseResponse<List<Tag>>> getTags();

    /** version */
    Observable<Version> getVersionDetails();

    /** common */
    //获得收藏结果
    Observable<BaseResponse<Collection>> getCollectRequest(int id);
    //获得取消收藏结果
    Observable<BaseResponse<Collection>> getUnCollectRequest(int id);
    //获得个人积分
    Observable<BaseResponse<UserCoin>> getUserCoin();
    //获得个人积分列表
    Observable<BaseResponse<Coins>> getCoins(int pageNum);
    //获得积分排行榜
    Observable<BaseResponse<CoinRanks>> getCoinRanks(int pageNum);

}
