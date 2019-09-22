package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BaseActivityPresenter;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.entity.CollectionRequest;
import com.example.hy.wanandroid.utlis.RxUtils;


import javax.inject.Inject;

/**
 * Collection的Presenter
 * Created by 陈健宇 at 2018/11/22
 */
public class CollectionPresenter extends BaseActivityPresenter<CollectionContract.View> implements CollectionContract.Presenter{

    @Inject
    public CollectionPresenter(DataModel model) {
        super(model);
    }


    @Override
    public void subscribeEvent() {
        super.subscribeEvent();
        addSubscriber(
                RxBus.getInstance().toObservable(TokenExpiresEvent.class)
                        .subscribe(tokenExpiresEvent -> loadCollections(0))
        );
    }

    @Override
    public void loadCollections(int pageNum) {
        addSubscriber(
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
        addSubscriber(
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

    @Override
    public void unCollectArticle(int id, int originalId) {
        addSubscriber(
                mModel.getUnCollectRequest(id, originalId)
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
