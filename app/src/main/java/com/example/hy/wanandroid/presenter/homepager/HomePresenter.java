package com.example.hy.wanandroid.presenter.homepager;

import android.annotation.SuppressLint;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.homepager.HomeModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;
import com.example.hy.wanandroid.utils.RxBus;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    private HomeContract.Model mHomeModel;

    @Inject
    public HomePresenter(HomeModel homeModel) {
        this.mHomeModel = homeModel;
    }

    @Override
    public void subscribleEvent() {
        super.subscribleEvent();
        addSubcriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                        .subscribe(toppingEvent -> mView.topping())
        );
    }

    @Override
    public void loadBannerDatas() {
        addSubcriber(
                mHomeModel.getBannerDatas()
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
                mHomeModel.getArticles(pageNum)
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
                mHomeModel.getArticles(pageNum)
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
}
