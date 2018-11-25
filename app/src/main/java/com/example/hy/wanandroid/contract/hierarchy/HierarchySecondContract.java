package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.hierarchy.SecondHierarchy;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;

import java.util.List;

import io.reactivex.Observable;

/**
 * 第二级体系文章列表的Contract
 * Created by 陈健宇 at 2018/10/28
 */
public interface HierarchySecondContract {

    interface View extends IView {
        void showArticles(List<Article> articleList);
        void showMoreArticles(List<Article> articleList);
        void topping();//置顶
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter extends IPresenter<HierarchySecondContract.View> {
        void loadArticles(int pageNum, int id);
        void loadMoreArticles(int pageNum, int id);
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

    interface Model{
        Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id);
        Observable<BaseResponse<Collection>> getCollectRequest(int id);//获得收藏结果
        Observable<BaseResponse<Collection>> getUnCollectRequest(int id);//获得取消收藏结果
    }
}
