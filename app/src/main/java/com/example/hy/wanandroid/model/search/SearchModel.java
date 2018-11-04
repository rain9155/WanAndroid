package com.example.hy.wanandroid.model.search;

import android.database.Cursor;
import android.text.TextUtils;

import com.example.hy.wanandroid.contract.search.SearchContract;
import com.example.hy.wanandroid.db.DbHelper;
import com.example.hy.wanandroid.db.DbHelperImp;
import com.example.hy.wanandroid.network.api.SearchApis;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.search.HotKey;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 搜索的model
 * Created by 陈健宇 at 2018/11/2
 */
public class SearchModel implements SearchContract.Model {

    private SearchApis mSearchApis;
    private DbHelper mDbHelper;

    @Inject
    public SearchModel(SearchApis searchApis, DbHelperImp dbHelperImp) {
        mSearchApis = searchApis;
        mDbHelper = dbHelperImp;
    }

    @Override
    public Observable<BaseResponse<Articles>> getSearchResquest(String key) {
        return mSearchApis.getSearchRequest(key);
    }

    @Override
    public Observable<BaseResponse<List<HotKey>>> getHotKey() {
        return mSearchApis.getHotKey();
    }

    @Override
    public boolean addHistoryRecord(String record) {
        return mDbHelper.addHistoryRecord(record);
    }

    @Override
    public List<String> getHistories() {
        return mDbHelper.getAllHistoryRecord();
    }

    @Override
    public int deleteOneHistoryRecord(String record) {
        return mDbHelper.deleteOneHistoryRecord(record);
    }

    @Override
    public int deleteAllHistoryRecord() {
        return mDbHelper.deleteAllHistoryRecord();
    }

    @Override
    public boolean isExistHistoryRecord(String record) {
        return mDbHelper.isExistHistoryRecord(record);
    }

}
