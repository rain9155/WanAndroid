package com.example.hy.wanandroid.presenter.homepager;

import android.annotation.SuppressLint;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.model.homepager.HomeModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefauleObserver;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 首页的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter{

    private HomeModel mHomeModel;

    @Inject
    public HomePresenter(HomeModel homeModel) {
        this.mHomeModel = homeModel;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadHomePagerDatas() {

    }

}
