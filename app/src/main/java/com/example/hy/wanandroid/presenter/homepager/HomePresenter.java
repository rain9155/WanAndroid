package com.example.hy.wanandroid.presenter.homepager;

import com.example.hy.wanandroid.base.presenter.BaseFragmentPresenter;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.event.NoImageEvent;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.entity.BaseResponse;
import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.entity.Articles;
import com.example.hy.wanandroid.entity.BannerData;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 首页的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class HomePresenter extends BaseFragmentPresenter<HomeContract.View> implements HomeContract.Presenter{


    @Inject
    public HomePresenter(DataModel dataModel) {
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
                RxBus.getInstance().toObservable(NoImageEvent.class)
                        .subscribe(noImageEvent -> mView.autoRefresh())
        );

        addSubscriber(
                RxBus.getInstance().toObservable(TokenExpiresEvent.class)
                        .subscribe(tokenExpiresEvent -> mView.collect())
        );
    }

    @Override
    public void loadBannerDatas() {
        addSubscriber(
                mModel.getBannerDatas()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<BannerData>>(mView, false, false) {
                    @Override
                    public void onNext(List<BannerData> bannerData) {
                        super.onNext(bannerData);
                        mView.showBannerDatas(bannerData);
                    }
                })
        );
    }

    @Override
    public void loadArticles(int pageNum) {
        addSubscriber(
                mModel.getArticles(pageNum)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<Articles>(mView) {
                    @Override
                    public void onNext(Articles articles) {
                        super.onNext(articles);
                        mView.showArticles(articles.getDatas());
                    }
                }));
    }

    @Override
    public void loadMoreArticles(int pageNum) {
        addSubscriber(
                mModel.getArticles(pageNum)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<Articles>(mView, false, false) {
                            @Override
                            public void onNext(Articles articles) {
                                super.onNext(articles);
                                mView.showMoreArticles(articles.getDatas());
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
                            }
                        })
        );
    }
}
