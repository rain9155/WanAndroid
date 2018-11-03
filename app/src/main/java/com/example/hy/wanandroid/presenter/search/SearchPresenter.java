package com.example.hy.wanandroid.presenter.search;

import android.text.TextUtils;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.search.SearchContract;
import com.example.hy.wanandroid.model.search.SearchModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.search.HotKey;
import com.example.hy.wanandroid.utils.CommonUtil;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 陈健宇 at 2018/11/2
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {

    private SearchContract.Model mSearchModel;

    @Inject
    public SearchPresenter(SearchModel searchModel) {
        mSearchModel = searchModel;
    }

    @Override
    public void loadHotkey() {
        addSubcriber(mSearchModel.getHotKey().subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<List<HotKey>>>() {
                    @Override
                    public void onNext(BaseResponse<List<HotKey>> listBaseResponse) {
                        mView.showHotKey(listBaseResponse.getData());
                    }
                }));
    }

    @Override
    public void loadSearchResquest(String key) {
        addSubcriber(mSearchModel.getSearchResquest(key).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<Articles>>() {
                    @Override
                    public void onNext(BaseResponse<Articles> listBaseResponse) {
                        mView.showSearchResquest(listBaseResponse.getData().getDatas());
                    }
                }));
    }

    @Override
    public void addHistoryRecord(String record) {
        if(!TextUtils.isEmpty(record.trim()) && !mSearchModel.isExistHistoryRecord(record))
            if(mSearchModel.addHistoryRecord(record))
                mView.addOneHistorySuccess(record);
    }

    @Override
    public void loadHistories() {
        List<String> histories = mSearchModel.getHistories();
        if(!CommonUtil.isEmptyList(histories)){
            Collections.reverse(histories);
            mView.showHistories(histories);
        }else {

        }
    }

    @Override
    public void deleteOneHistoryRecord(String record) {
        if(1 == mSearchModel.deleteOneHistoryRecord(record)) mView.deleteOneHistorySuccess(record);
    }

    @Override
    public void deleteAllHistoryRecord() {
        if(mSearchModel.deleteAllHistoryRecord() > 0) mView.deleteAllHistoryRecordSuccess();
    }
}
