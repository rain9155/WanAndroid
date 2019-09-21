package com.example.hy.wanandroid.presenter.hierarchy;

import com.example.hy.wanandroid.base.presenter.BaseFragmentPresenter;
import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.entity.BaseResponse;
import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.SecondHierarchy;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

/**
 * Created by 陈健宇 at 2018/10/29
 */
public class HierarchySecondPresenter extends BaseFragmentPresenter<HierarchySecondContract.View> implements HierarchySecondContract.Presenter {

    @Inject
    public HierarchySecondPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribeEvent() {
        super.subscribeEvent();
        addSubscriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                .subscribe(toppingEvent -> mView.topping())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(CollectionEvent.class)
                        .subscribe(collectionEvent -> mView.refreshCollections(collectionEvent.getIds()))
        );

        addSubscriber(
                RxBus.getInstance().toObservable(TokenExpiresEvent.class)
                .subscribe(tokenExpiresEvent -> mView.collect())
        );
    }

    @Override
    public void loadArticles(int pageNum, int id) {
        addSubscriber(
                mModel.getArticles(pageNum, id)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<SecondHierarchy>(mView) {
                    @Override
                    public void onNext(SecondHierarchy secondHierarchy) {
                        super.onNext(secondHierarchy);
                        mView.showArticles(secondHierarchy.getDatas());
                    }
                }));
    }

    @Override
    public void loadMoreArticles(int pageNum, int id) {
        addSubscriber(
                mModel.getArticles(pageNum, id)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<SecondHierarchy>(mView, false, false) {
                            @Override
                            public void onNext(SecondHierarchy secondHierarchy) {
                                super.onNext(secondHierarchy);
                                mView.showMoreArticles(secondHierarchy.getDatas());
                            }
                        }));
    }

    @Override
    public void collectArticle(int id) {
        addSubscriber(
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
        addSubscriber(
                mModel.getUnCollectRequest(id)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<BaseResponse<Collection>>(mView, false, false){
                            @Override
                            public void onNext(BaseResponse<Collection> baseResponse) {
                                super.onNext(baseResponse);
                                mView.unCollectArticleSuccess();
                            }})
        );
    }

}
