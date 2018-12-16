package com.example.hy.wanandroid.presenter.search;

import android.text.TextUtils;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.search.SearchContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Articles;
import com.example.hy.wanandroid.model.network.entity.HotKey;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 陈健宇 at 2018/11/2
 */
public class SearchPresenter extends BasePresenter<SearchContract.View> implements SearchContract.Presenter {


    @Inject
    public SearchPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void loadHotkey() {
        addSubcriber(
                mModel.getHotKey()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<HotKey>>(mView, false, false) {
                    @Override
                    public void onNext(List<HotKey> hotKeys) {
                        super.onNext(hotKeys);
                        if(CommonUtil.isEmptyList(hotKeys)) mView.showHotHintLayout();
                        else  mView.showHotKey(hotKeys);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showHotHintLayout();
                    }
                })
        );
    }

    @Override
    public void loadSearchResquest(String key, int pageNum) {
        if(TextUtils.isEmpty(key.trim())) return;
        addSubcriber(
                mModel.getSearchResquest(key, pageNum)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<Articles>(mView) {
                    @Override
                    public void onNext(Articles articles) {
                        super.onNext(articles);
                        if(articles.getDatas().size() != 0){
                            mView.showSearchResquest(articles.getDatas());
                        }else {
                            mView.showEmptyLayout();
                        }
                        mView.hideHistoryHotLayout();
                        mView.showSearchRequestLayout();
                    }
                })
        );
    }

    @Override
    public void loadSearchMoreResquest(String key, int pageNum) {
        if(TextUtils.isEmpty(key.trim())) return;
        addSubcriber(
                mModel.getSearchResquest(key, pageNum)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<Articles>(mView, false, false) {
                            @Override
                            public void onNext(Articles articles) {
                                super.onNext(articles);
                                mView.showSearchMoreResquest(articles.getDatas());
                            }
                        })
        );
    }

    @Override
    public void addHistoryRecord(String record) {
        if(TextUtils.isEmpty(record.trim()) || mModel.isExistHistoryRecord(record)) return;
        if(!mModel.addHistoryRecord(record)) return;
        mView.addOneHistorySuccess(record);
        mView.hideHistoryHintLayout();
    }

    @Override
    public void loadHistories() {
        List<String> histories = mModel.getAllHistoryRecord();
        if(!CommonUtil.isEmptyList(histories)){
            Collections.reverse(histories);
            mView.showHistories(histories);
        }else {
            mView.showHistoryHintLayout();
        }
    }

    @Override
    public void deleteOneHistoryRecord(String record) {
        if(1 == mModel.deleteOneHistoryRecord(record)) mView.deleteOneHistorySuccess(record);
    }

    @Override
    public void deleteAllHistoryRecord() {
        if(mModel.deleteAllHistoryRecord() > 0){
            mView.deleteAllHistoryRecordSuccess();
            mView.showHistoryHintLayout();
        }
    }

    @Override
    public void clearAllSearchKey(String key) {
        if(!TextUtils.isEmpty(key.trim())) return;
        mView.hideSearchRequestLayout();
        mView.showHistoryHotLayout();
        mView.hideEmptyLayout();
    }

    @Override
    public void collectArticle(int id) {
        addSubcriber(
                mModel.getCollectRequest(id)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<BaseResponse<Collection>>(mView, false, false){
                            @Override
                            public void onNext(BaseResponse<Collection> baseResponse) {
                                super.onNext(baseResponse);
                                mView.collectArticleSuccess();
                            }
                        })
        );
    }

    @Override
    public void unCollectArticle(int id) {
        addSubcriber(
                mModel.getUnCollectRequest(id)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<BaseResponse<Collection>>(mView, false, false){
                            @Override
                            public void onNext(BaseResponse<Collection> baseResponse) {
                                super.onNext(baseResponse);
                                mView.unCollectArticleSuccess();
                            }
                        })
        );
    }
}
