package com.example.hy.wanandroid.contract.search;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Article;
import com.example.hy.wanandroid.model.network.entity.Articles;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.model.network.entity.HotKey;

import java.util.List;

import io.reactivex.Observable;

/**
 * 搜索的Contract
 * Created by 陈健宇 at 2018/11/2
 */
public interface SearchContract {


    interface View extends BaseView {
        void showHotKey(List<HotKey> hotKeyList);//显示搜索热词
        void showHistories(List<String> histories);//显示搜索历史
        void addOneHistorySuccess(String record);//添加一条搜索历史成功
        void deleteOneHistorySuccess(String record);//删除一条搜索历史成功
        void deleteAllHistoryRecordSuccess();//删除所有搜索历史成功
        void showHistoryHintLayout();//显示搜索提示布局
        void hideHistoryHintLayout();//隐藏搜索提示布局
        void showHotHintLayout();//显示热词提示布局
        void showSearchResquest(List<Article> searchRequestList);//显示搜索请求
        void showSearchMoreResquest(List<Article> searchRequestList);//显示更多搜索请求
        void showHistoryHotLayout();//显示搜索热词布局
        void hideHistoryHotLayout();//隐藏搜索热词布局
        void showSearchRequestLayout();//显示搜索热词布局
        void hideSearchRequestLayout();//隐藏搜索热词布局
        void showEmptyLayout();
        void hideEmptyLayout();
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter extends IPresenter<View>{
        void loadHotkey();//加载搜索热词
        void loadSearchResquest(String key, int pageNum);//加载搜索请求
        void loadSearchMoreResquest(String key, int pageNum);//加载更多搜索请求
        void loadHistories();//加载搜索历史
        void addHistoryRecord(String record);//添加搜索历史
        void deleteOneHistoryRecord(String record);//删除一条搜索历史
        void deleteAllHistoryRecord();//删除所有搜索历史
        void clearAllSearchKey(String key);//清空了所有的搜索
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

    interface Model{
        Observable<BaseResponse<Articles>> getSearchResquest(String key, int pageNum);//获得搜索结果
        Observable<BaseResponse<List<HotKey>>> getHotKey();//获得搜索热词
        boolean addHistoryRecord(String record);//添加搜索历史是否成功
        List<String> getHistories();//获得搜索历史
        int deleteOneHistoryRecord(String record);//删除一条搜索历史
        int deleteAllHistoryRecord();//删除所有搜索历史
        boolean isExistHistoryRecord(String record);//判断搜索历史是否存在
        Observable<BaseResponse<Collection>> getCollectRequest(int id);//获得收藏结果
        Observable<BaseResponse<Collection>> getUnCollectRequest(int id);//获得取消收藏结果
    }

}
