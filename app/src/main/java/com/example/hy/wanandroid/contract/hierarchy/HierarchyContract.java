package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.hierarchy.FirstHierarchy;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface HierarchyContract {

    interface View extends IView {
        void showFirstHierarchyList(List<FirstHierarchy> firstHierarchyList);
        void showMoreFirstHierarchyList(List<FirstHierarchy> firstHierarchyList);
        void topping();//置顶
    }

    interface Presenter extends IPresenter<HierarchyContract.View> {
        void loadFirstHierarchyList();
        void loadMoreFirstHierarchyList();
    }

    interface Model{
        Observable<BaseResponse<List<FirstHierarchy>>> getFirstHierarchyList();
    }

}
