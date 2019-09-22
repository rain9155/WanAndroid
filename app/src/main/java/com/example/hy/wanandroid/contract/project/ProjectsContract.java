package com.example.hy.wanandroid.contract.project;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Article;

import java.util.List;

/**
 * 项目列表的Contract
 * Created by 陈健宇 at 2018/10/29
 */
public interface ProjectsContract {

    interface View extends IView {
        void showProjects(List<Article> articleList);//展示项目列表
        void showMoreProjects(List<Article> articleList);//展示更多项目列表
        void topping();//置顶
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
        void collect();
        void refreshCollections(List<Integer> ids);//刷新文章列表中的收藏
        void autoRefresh();//自动刷新
    }

    interface Presenter{
        void loadProjects(int pageNum, int id);//加载项目列表
        void loadMoreProjects(int pageNum, int id);//加载更多项目列表
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

}
