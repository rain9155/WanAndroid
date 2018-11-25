package com.example.hy.wanandroid.model.hierarchy;

import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.core.network.api.HierarchyApis;
import com.example.hy.wanandroid.core.network.api.HomeApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.hierarchy.SecondHierarchy;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/29
 */
public class HierarchySecondModel implements HierarchySecondContract.Model {

    private HierarchyApis mHierarchyApis;
    private HomeApis mHomeApis;

    @Inject
    public HierarchySecondModel(HierarchyApis hierarchyApis, HomeApis homeApis) {
        this.mHierarchyApis = hierarchyApis;
        this.mHomeApis = homeApis;
    }

    @Override
    public Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id) {
        return mHierarchyApis.getSecondHierarchyList(pageNum, id);
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
