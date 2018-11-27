package com.example.hy.wanandroid.contract;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * Mian活动
 * Created by 陈健宇 at 2018/10/23
 */
public interface MainContract {

    interface View extends BaseView {

    }

    interface Presenter extends IPresenter<MainContract.View>{
        void setCurrentItem(int position);
        int getCurrentItem();
    }
}
