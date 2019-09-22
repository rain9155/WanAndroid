package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.FirstHierarchy;

import java.util.List;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface HierarchyContract {

    interface View extends IView {
        void showFirstHierarchyList(List<FirstHierarchy> firstHierarchyList);
        void showMoreFirstHierarchyList(List<FirstHierarchy> firstHierarchyList);
        void topping();//置顶
    }

    interface Presenter{
        void loadFirstHierarchyList();
        void loadMoreFirstHierarchyList();
    }


}
