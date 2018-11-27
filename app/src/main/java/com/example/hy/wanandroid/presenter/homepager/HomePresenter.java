package com.example.hy.wanandroid.presenter.homepager;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.mine.Collection;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.homepager.Articles;
import com.example.hy.wanandroid.model.network.entity.homepager.BannerData;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 首页的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{


    @Inject
    public HomePresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void subscribleEvent() {
        addSubcriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                        .subscribe(toppingEvent -> mView.topping())
        );

        addSubcriber(
                RxBus.getInstance().toObservable(CollectionEvent.class)
                .subscribe(collectionEvent -> mView.refreshCollections(collectionEvent.getIds()))
        );
    }

    @Override
    public void loadBannerDatas() {
        addSubcriber(
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
        addSubcriber(
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
        addSubcriber(
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
