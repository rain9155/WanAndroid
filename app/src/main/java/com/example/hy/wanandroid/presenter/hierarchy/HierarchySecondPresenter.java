package com.example.hy.wanandroid.presenter.hierarchy;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.model.hierarchy.HierarchySecondModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.hierarchy.SecondHierarchy;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 陈健宇 at 2018/10/29
 */
public class HierarchySecondPresenter extends BasePresenter<HierarchySecondContract.View> implements HierarchySecondContract.Presenter {

    private HierarchySecondContract.Model mHierarchySecondListModel;

    @Inject
    public HierarchySecondPresenter(HierarchySecondModel hierarchySecondListModel) {
        mHierarchySecondListModel = hierarchySecondListModel;
    }

    @Override
    public void loadArticles(int pageNum, int id) {
        addSubcriber(
                mHierarchySecondListModel.getArticles(pageNum, id)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<SecondHierarchy>(mView) {
                    @Override
                    public void onNext(SecondHierarchy secondHierarchy) {
                        super.onNext(secondHierarchy);
                        mView.showArticles(secondHierarchy.getDatas());
                    }
                }));
    }

    @Override
    public void loadMoreArticles(int pageNum, int id) {
        addSubcriber(
                mHierarchySecondListModel.getArticles(pageNum, id)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<SecondHierarchy>(mView, false, false) {
                            @Override
                            public void onNext(SecondHierarchy secondHierarchy) {
                                super.onNext(secondHierarchy);
                                mView.showMoreArticles(secondHierarchy.getDatas());
                            }
                        }));
    }

}
