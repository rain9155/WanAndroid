package com.example.hy.wanandroid.contract.homepager;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;
import com.example.hy.wanandroid.core.network.entity.homepager.Articles;
import com.example.hy.wanandroid.core.network.entity.homepager.BannerData;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Path;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface HomeContract {

    interface View extends IView {
        void showBannerDatas(List<BannerData> bannerDataList);//展示banner数据
        void showArticles(List<Article> articleList);//展示首页文章数据
        void showMoreArticles(List<Article> articleList);//加载更多文章数据
        void topping();//置顶
        void collectArticleSuccess();//收藏文章成功
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter extends IPresenter<HomeContract.View> {
        void loadBannerDatas();//加载首页banner数据
        void loadArticles(int pageNum);//加载首页文章数据
        void loadMoreArticles(int pageNum);//加载更多首页文章数据
        void collectArticle(int id);//收藏文章
        void unCollectArticle(int id);//取消收藏
    }

    interface Model{
        Observable<BaseResponse<List<BannerData>>> getBannerDatas();//获得banner数据
        Observable<BaseResponse<Articles>> getArticles(int pageNum);//获得首页文章数据
        Observable<BaseResponse<Collection>> getCollectRequest(int id);//获得收藏结果
        Observable<BaseResponse<Collection>> getUnCollectRequest(int id);//获得取消收藏结果
    }

}
