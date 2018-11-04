package com.example.hy.wanandroid.presenter.hierarchy;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.model.hierarchy.HierarchyModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 体系的Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class HierarchyPresenter extends BasePresenter<HierarchyContract.View> implements HierarchyContract.Presenter{


    private HierarchyContract.Model mHierarchyModel;

    @Inject
    public HierarchyPresenter(HierarchyModel hierarchyModel) {
        this.mHierarchyModel = hierarchyModel;
    }

    @Override
    public void loadFirstHierarchyList() {
        addSubcriber(mHierarchyModel.getFirstHierarchyList().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DefaultObserver<BaseResponse<List<FirstHierarchy>>>(mView) {
                    @Override
                    public void onNext(BaseResponse<List<FirstHierarchy>> listBaseResponse) {
                        super.onNext(listBaseResponse);
                        mView.showFirstHierarchyList(listBaseResponse.getData());
                    }
                }));
    }
}
