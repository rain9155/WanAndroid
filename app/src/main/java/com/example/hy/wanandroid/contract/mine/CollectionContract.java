package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Collection;

import java.util.List;

/**
 * Collection的Contract
 * Created by 陈健宇 at 2018/11/22
 */
public interface CollectionContract {

    interface View extends IView {
        void showCollections(List<Collection> collections);
        void showMoreCollections(List<Collection> collections);
        void showEmptyLayout();
        void unCollectArticleSuccess();//取消收藏成功
    }

    interface Presenter {
        void loadCollections(int pageNum);
        void loadMoreCollections(int pageNum);
        void unCollectArticle(int id, int original);//取消收藏
    }

}
