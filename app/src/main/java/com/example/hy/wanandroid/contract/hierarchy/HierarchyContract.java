package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.contract.MainContract;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface HierarchyContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<HierarchyContract.View> {

    }

    interface Model{

    }

}
