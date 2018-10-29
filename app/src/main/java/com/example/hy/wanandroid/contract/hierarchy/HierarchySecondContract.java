package com.example.hy.wanandroid.contract.hierarchy;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.hierarchy.SecondHierarchy;
import com.example.hy.wanandroid.network.entity.homepager.Article;

import java.util.List;

import io.reactivex.Observable;

/**
 * 第二级体系文章列表的Contract
 * Created by 陈健宇 at 2018/10/28
 */
public interface HierarchySecondContract {

    interface View extends IView {
        void showArticles(List<Article> articleList);
    }

    interface Presenter extends IPresenter<HierarchySecondContract.View> {
        void loadArticles(int pageNum, int id);
    }

    interface Model{
        Observable<BaseResponse<SecondHierarchy>> getArticles(int pageNum, int id);
    }
}
