package com.example.hy.wanandroid.presenter.hierarchy;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.hierarchy.HierarchyModel;
import com.example.hy.wanandroid.core.network.entity.DefaultObserver;
import com.example.hy.wanandroid.core.network.entity.hierarchy.FirstHierarchy;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

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
        addSubcriber(
                mHierarchyModel.getFirstHierarchyList()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<FirstHierarchy>>(mView) {
                    @Override
                    public void onNext(List<FirstHierarchy> firstHierarchies) {
                        super.onNext(firstHierarchies);
                        mView.showFirstHierarchyList(firstHierarchies);
                    }
                }));
    }

    @Override
    public void loadMoreFirstHierarchyList() {
        addSubcriber(
                mHierarchyModel.getFirstHierarchyList()
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<List<FirstHierarchy>>(mView, false, false) {
                            @Override
                            public void onNext(List<FirstHierarchy> firstHierarchies) {
                                super.onNext(firstHierarchies);
                                mView.showMoreFirstHierarchyList(firstHierarchies);
                            }
                        }));
    }

    @Override
    public void subscribleEvent() {
        addSubcriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                        .subscribe(toppingEvent -> mView.topping())
        );
    }
}
