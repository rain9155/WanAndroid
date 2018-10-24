package com.example.hy.wanandroid.contract;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.view.MainActivity;

/**
 * Mian活动
 * Created by 陈健宇 at 2018/10/23
 */
public interface MainContract {

    interface View extends IView{

    }

    interface Presenter extends IPresenter<MainContract.View>{

    }

    interface Model{

    }

}
