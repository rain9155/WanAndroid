package com.example.hy.wanandroid.presenter.homepager;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.homepager.ArticleContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.mine.Collection;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * 文章详情的Presenter
 * Created by 陈健宇 at 2018/11/8
 */
public class ArticlePresenter extends BasePresenter<ArticleContract.View> implements ArticleContract.Presenter {


    @Inject
    public ArticlePresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void collectArticle(int id) {
        addSubcriber(
                mModel.getCollectRequest(id)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<BaseResponse<Collection>>(mView, false, false){
                            @Override
                            public void onNext(BaseResponse<Collection> baseResponse) {
                                super.onNext(baseResponse);
                                mView.collectArticleSuccess();
                            }

                            @Override
                            protected void tokenExpire() {
                                super.tokenExpire();
                                mView.tokenExpire(Constant.REQUEST_COLLECT_ARTICLE);
                            }
                        })
        );
    }

    @Override
    public void unCollectArticle(int id) {
        addSubcriber(
                mModel.getUnCollectRequest(id)
                        .compose(RxUtils.switchSchedulers())
                        .subscribeWith(new DefaultObserver<BaseResponse<Collection>>(mView, false, false){
                            @Override
                            public void onNext(BaseResponse<Collection> baseResponse) {
                                super.onNext(baseResponse);
                                mView.unCollectArticleSuccess();
                            }

                            @Override
                            protected void tokenExpire() {
                                super.tokenExpire();
                                mView.tokenExpire(Constant.REQUEST_COLLECT_ARTICLE);
                            }
                        })
        );
    }
}
