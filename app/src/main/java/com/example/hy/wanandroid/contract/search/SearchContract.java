package com.example.hy.wanandroid.contract.search;

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
        void showHotKey(List<HotKey> hotKeyList);
        void showSearchResquest(List<Article> searchRequestList);
        void showHistories(List<String> histories);
        void addOneHistory(String record);
    }

    interface Presenter extends IPresenter<View>{
        void loadHotkey();
        void loadSearchResquest(String key);
        void addHistoryRecord(String record);
        void loadHistories();
        void deleteOneHistoryRecord(String record);
        void deleteAllHistoryRecord();
    }

    interface Model{
        Observable<BaseResponse<Articles>> getSearchResquest(String key);
        Observable<BaseResponse<List<HotKey>>> getHotKey();
        boolean addHistoryRecord(String record);
        List<String> getHistories();
        void deleteOneHistoryRecord(String record);
        void deleteAllHistoryRecord();
        boolean isExistHistoryRecord(String record);
    }

}
