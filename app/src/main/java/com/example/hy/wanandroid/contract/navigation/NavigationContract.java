package com.example.hy.wanandroid.contract.navigation;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.navigation.Tag;

import java.util.List;

import io.reactivex.Observable;

/**
 * 导航的Contract
 * Created by 陈健宇 at 2018/10/31
 */
public interface NavigationContract {

    interface View extends IView{
        void showTags(List<Tag> tagList);
    }

    interface Presenter extends IPresenter<NavigationContract.View>{
        void loadTags();
    }

    interface Model{
        Observable<BaseResponse<List<Tag>>> getTags();
    }
}
