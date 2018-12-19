package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.SecondHierarchy;
import com.example.hy.wanandroid.model.network.entity.Article;
import com.example.hy.wanandroid.model.network.entity.Collection;

import java.util.List;

import io.reactivex.Observable;

/**
 * 第二级体系文章列表的Contract
 * Created by 陈健宇 at 2018/10/28
 */
public interface HierarchySecondContract {

    interface View extends BaseView {
        void showArticles(List<Article> articleList);
        void showMoreArticles(List<Article> articleList);
        void topping();//置顶
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
        void collect();
        void refreshCollections(List<Integer> ids);//刷新文章列表中的收藏
    }

    interface Presenter extends IPresenter<HierarchySecondContract.View> {
        void loadArticles(int pageNum, int id);
        void loadMoreArticles(int pageNum, int id);
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

}
