package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.model.network.entity.Collection;

import java.util.List;

/**
 * Collection的Contract
 * Created by 陈健宇 at 2018/11/22
 */
public interface CollectionContract {

    interface View extends BaseView {
        void showCollections(List<Collection> collections);
        void showMoreCollections(List<Collection> collections);
        void showEmptyLayout();
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter extends BasePresenter<View> {
        void loadCollections(int pageNum);
        void loadMoreCollections(int pageNum);
        void unCollectArticle(int id, int original);//取消收藏
    }

}
