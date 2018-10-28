package com.example.hy.wanandroid.model.hierarchy;

import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.network.api.HierarchyApis;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 体系的model
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchyModel implements HierarchyContract.Model {

    private HierarchyApis mHierarchyApis;

    @Inject
    public HierarchyModel(HierarchyApis hierarchyApis) {
        this.mHierarchyApis = hierarchyApis;
    }

    @Override
    public Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList() {
        return mHierarchyApis.getFirstHierarchyList();
    }
}
