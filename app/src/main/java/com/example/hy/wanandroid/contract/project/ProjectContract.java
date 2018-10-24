package com.example.hy.wanandroid.contract.project;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.contract.MainContract;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface ProjectContract {

    interface View extends IView {

    }

    interface Presenter extends IPresenter<ProjectContract.View> {

    }

    interface Model{

    }
}
