package com.example.hy.wanandroid.model.hierarchy;

import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.core.network.api.HierarchyApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.hierarchy.SecondHierarchy;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/29
 */
public class HierarchySecondModel implements HierarchySecondContract.Model {

    private HierarchyApis mHierarchyApis;

    @Inject
    public HierarchySecondModel(HierarchyApis hierarchyApis) {
        this.mHierarchyApis = hierarchyApis;
    }

    @Override
    public Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id) {
        return mHierarchyApis.getSecondHierarchyList(pageNum, id);
    }
}
