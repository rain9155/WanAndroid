package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;

/**
 * 第二级体系的Contract
 * Created by 陈健宇 at 2018/10/28
 */
public interface HierarchySecondContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<HierarchySecondContract.View> {
    }

    interface Model{
    }
}
