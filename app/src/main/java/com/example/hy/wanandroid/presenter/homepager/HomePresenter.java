package com.example.hy.wanandroid.presenter.homepager;

import android.annotation.SuppressLint;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.model.homepager.HomeModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;

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
    public void loadBannerDatas() {
        addSubcriber(mHomeModel.getBannerDatas().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<List<BannerData>>>() {
                    @Override
                    public void onNext(BaseResponse<List<BannerData>> listBaseResponse) {
                        mView.showBannerDatas(listBaseResponse.getData());
                    }
                }));
    }

    @Override
    public void loadArticles(int pageNum) {
        addSubcriber(mHomeModel.getArticles(pageNum).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<Articles>>() {
                    @Override
                    public void onNext(BaseResponse<Articles> articlesBaseResponse) {
                        mView.showArticles(articlesBaseResponse.getData().getDatas());
                    }
                }));
    }
}
