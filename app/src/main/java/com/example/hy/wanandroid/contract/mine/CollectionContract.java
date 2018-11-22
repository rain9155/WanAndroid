package com.example.hy.wanandroid.contract.mine;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;
import com.example.hy.wanandroid.core.network.entity.mine.CollectionRequest;

import java.util.List;

import io.reactivex.Observable;

/**
 * Collection的Contract
 * Created by 陈健宇 at 2018/11/22
 */
public interface CollectionContract {

    interface View extends IView{
        void showCollections(List<Collection> collections);
        void showMoreCollections(List<Collection> collections);
    }

    interface Presenter extends IPresenter<View>{
        void loadCollections(int pageNum);
        void loadMoreCollections(int pageNum);

    }

    interface Model{
        Observable<BaseResponse<CollectionRequest>> getCollectionRequest(int pageNum);//获得收藏结果
    }
}
