package com.example.hy.wanandroid.contract.navigation;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Tag;

import java.util.List;

import io.reactivex.Observable;

/**
 * 导航的Contract
 * Created by 陈健宇 at 2018/10/31
 */
public interface NavigationContract {

    interface View extends BaseView {
        void showTags(List<Tag> tagList);//显示tag标签
        void showTagsName(List<String> tagsName);//显示tag标签名字
    }

    interface Presenter extends IPresenter<NavigationContract.View>{
        void loadTags();//加载tag标签
    }

}
