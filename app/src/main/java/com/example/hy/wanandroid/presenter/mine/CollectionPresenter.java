package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.core.network.entity.DefaultObserver;
import com.example.hy.wanandroid.core.network.entity.mine.CollectionRequest;
import com.example.hy.wanandroid.model.mine.CollectionModel;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * Collection的Presenter
 * Created by 陈健宇 at 2018/11/22
 */
public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter{

    private CollectionContract.Model mModel;

    @Inject
    public CollectionPresenter(CollectionModel model) {
        mModel = model;
    }

    @Override
    public void loadCollections(int pageNum) {
        addSubcriber(
                mModel.getCollectionRequest(pageNum)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<CollectionRequest>(mView){
                    @Override
                    public void onNext(CollectionRequest collectionRequest) {
                        super.onNext(collectionRequest);
                        mView.showCollections(collectionRequest.getDatas());
                    }
                })
        );
    }

    @Override
    public void loadMoreCollections(int pageNum) {
        addSubcriber(
                mModel.getCollectionRequest(pageNum)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<CollectionRequest>(mView, false, false){
                            @Override
                            public void onNext(CollectionRequest collectionRequest) {
                                super.onNext(collectionRequest);
                                mView.showMoreCollections(collectionRequest.getDatas());
                            }
                        })
        );
    }

}
