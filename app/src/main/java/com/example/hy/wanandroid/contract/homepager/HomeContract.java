package com.example.hy.wanandroid.contract.homepager;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.Articles;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by 陈健宇 at 2018/10/23
 */
public interface HomeContract {

    interface View extends IView {
        void showBannerDatas(List<BannerData> bannerDataList);//展示banner数据
        void showArticles(List<Article> articleList);//展示首页文章数据
    }

    interface Presenter extends IPresenter<HomeContract.View> {
        void loadBannerDatas();//加载首页banner数据
        void loadArticles(int pageNum);//加载首页文章数据
    }

    interface Model{
        Observable<BaseResponse<List<BannerData>>> getBannerDatas();//获得banner数据
        Observable<BaseResponse<Articles>> getArticles(int pageNum);//获得首页文章数据
    }

}
