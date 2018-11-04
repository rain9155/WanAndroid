package com.example.hy.wanandroid.contract.search;

import android.database.Cursor;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.search.HotKey;

import java.util.List;

import io.reactivex.Observable;

/**
 * 搜索的Contract
 * Created by 陈健宇 at 2018/11/2
 */
public interface SearchContract {


    interface View extends IView{
        void showHotKey(List<HotKey> hotKeyList);//显示搜索热词
        void showSearchResquest(List<Article> searchRequestList);//显示搜索请求
        void showHistories(List<String> histories);//显示搜索历史
        void addOneHistorySuccess(String record);//添加一条搜索历史成功
        void deleteOneHistorySuccess(String record);//删除一条搜索历史成功
        void deleteAllHistoryRecordSuccess();//删除所有搜索历史成功
        void showHistoryHintLayout();//显示搜索提示布局
        void hideHistoryHintLayout();//隐藏搜索提示布局
        void showHotHintLayout();//显示热词提示布局
    }

    interface Presenter extends IPresenter<View>{
        void loadHotkey();//加载搜索热词
        void loadSearchResquest(String key);//加载搜索请求
        void loadHistories();//加载搜索历史
        void addHistoryRecord(String record);//添加搜索历史
        void deleteOneHistoryRecord(String record);//删除一条搜索历史
        void deleteAllHistoryRecord();//删除所有搜索历史
    }

    interface Model{
        Observable<BaseResponse<Articles>> getSearchResquest(String key);//获得搜索结果
        Observable<BaseResponse<List<HotKey>>> getHotKey();//获得搜索热词
        boolean addHistoryRecord(String record);//添加搜索历史是否成功
        List<String> getHistories();//获得搜索历史
        int deleteOneHistoryRecord(String record);//删除一条搜索历史
        int deleteAllHistoryRecord();//删除所有搜索历史
        boolean isExistHistoryRecord(String record);//判断搜索历史是否存在
    }

}
