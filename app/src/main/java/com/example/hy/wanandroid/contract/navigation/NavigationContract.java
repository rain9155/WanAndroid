package com.example.hy.wanandroid.contract.navigation;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.entity.Tag;

import java.util.List;

/**
 * 导航的Contract
 * Created by 陈健宇 at 2018/10/31
 */
public interface NavigationContract {

    interface View extends BaseView {
        void showTags(List<Tag> tagList);//显示tag标签
        void showTagsName(List<String> tagsName);//显示tag标签名字
    }

    interface Presenter extends BasePresenter<View> {
        void loadTags();//加载tag标签
    }

}
