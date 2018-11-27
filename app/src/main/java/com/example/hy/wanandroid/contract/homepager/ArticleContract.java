package com.example.hy.wanandroid.contract.homepager;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.mine.Collection;

import io.reactivex.Observable;

/**
 * 文章详情的Contract
 * Created by 陈健宇 at 2018/11/8
 */
public interface ArticleContract {

    interface View extends BaseView {
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter extends IPresenter<View>{
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

    interface Model{
        Observable<BaseResponse<Collection>> getCollectRequest(int id);//获得收藏结果
        Observable<BaseResponse<Collection>> getUnCollectRequest(int id);//获得取消收藏结果
    }
}
