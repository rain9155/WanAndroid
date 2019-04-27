package com.example.hy.wanandroid.contract.homepager;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;

/**
 * 文章详情的Contract
 * Created by 陈健宇 at 2018/11/8
 */
public interface ArticleContract {

    interface View extends BaseView {
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
        void collect();
    }

    interface Presenter extends BasePresenter<View> {
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }
}
