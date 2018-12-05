package com.example.hy.wanandroid.presenter.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.model.network.entity.CollectionRequest;
import com.example.hy.wanandroid.utils.RxUtils;


import javax.inject.Inject;

/**
 * Collection的Presenter
 * Created by 陈健宇 at 2018/11/22
 */
public class CollectionPresenter extends BasePresenter<CollectionContract.View> implements CollectionContract.Presenter{

    @Inject
    public CollectionPresenter(DataModel model) {
        super(model);
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

                    @Override
                    protected void tokenExpire() {
                        super.tokenExpire();
                        mView.tokenExpire(Constant.REQUEST_SHOW_COLLECTIONS);
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

    @Override
    public void unCollectArticle(int id, int originalId) {
        addSubcriber(
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
