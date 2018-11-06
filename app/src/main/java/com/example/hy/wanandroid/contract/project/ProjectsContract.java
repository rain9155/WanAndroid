package com.example.hy.wanandroid.contract.project;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.project.Project;

import java.util.List;

import io.reactivex.Observable;

/**
 * 项目列表的Contract
 * Created by 陈健宇 at 2018/10/29
 */
public interface ProjectsContract {

    interface View extends IView {
        void showProjects(List<Article> articleList);//展示项目列表
        void showMoreProjects(List<Article> articleList);//展示更多项目列表
        void topping();//置顶
    }

    interface Presenter extends IPresenter<ProjectsContract.View> {
        void loadProjects(int pageNum, int id);//加载项目列表
        void loadMoreProjects(int pageNum, int id);//加载更多项目列表
    }

    interface Model{
        Observable<BaseResponse<Articles>> getProjects(int pageNum, int id);//获得项目列表
    }
}
