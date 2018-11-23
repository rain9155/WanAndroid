package com.example.hy.wanandroid.model.homepager;

import com.example.hy.wanandroid.contract.homepager.ArticleContract;
import com.example.hy.wanandroid.core.network.api.HomeApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Article的Model
 * Created by 陈健宇 at 2018/11/23
 */
public class ArticleModel implements ArticleContract.Model {

    private HomeApis mHomeApis;

    @Inject
    public ArticleModel(HomeApis homeApis) {
        mHomeApis = homeApis;
    }

    @Override
    public Observable<BaseResponse<Collection>> getCollectRequest(int id) {
        return mHomeApis.getCollectRequest(id);
    }

    @Override
    public Observable<BaseResponse<Collection>> getUnCollectRequest(int id) {
        return mHomeApis.getUnCollectRequest(id);
    }
}
