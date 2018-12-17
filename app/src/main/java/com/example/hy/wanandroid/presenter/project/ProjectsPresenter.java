package com.example.hy.wanandroid.presenter.project;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.project.ProjectsContract;
import com.example.hy.wanandroid.event.AutoCacheEvent;
import com.example.hy.wanandroid.event.NoImageEvent;
import com.example.hy.wanandroid.event.TokenExpiresEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Articles;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.utlis.RxUtils;

import javax.inject.Inject;

/**
 * 详细项目分类的Presenter
 * Created by 陈健宇 at 2018/10/30
 */
public class ProjectsPresenter extends BasePresenter<ProjectsContract.View> implements ProjectsContract.Presenter {


    @Inject
    public ProjectsPresenter(DataModel dataModel) {
       super(dataModel);
    }

    @Override
    public void subscribleEvent() {
        super.subscribleEvent();
        addSubcriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                .subscribe(toppingEvent -> mView.topping())
        );
        addSubcriber(
                RxBus.getInstance().toObservable(CollectionEvent.class)
                        .subscribe(collectionEvent -> mView.refreshCollections(collectionEvent.getIds()))
        );
        addSubcriber(
                RxBus.getInstance().toObservable(NoImageEvent.class)
                        .subscribe(noImageEvent -> mView.autoRefresh())
        );
        addSubcriber(
                RxBus.getInstance().toObservable(AutoCacheEvent.class)
                        .subscribe(noImageEvent -> mView.autoRefresh())
        );
        addSubcriber(
                RxBus.getInstance().toObservable(TokenExpiresEvent.class)
                .subscribe(tokenExpiresEvent -> mView.collect())
        );
    }

    @Override
    public void loadProjects(int pageNum, int id) {
        addSubcriber(
                mModel.getProjects(pageNum, id)
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<Articles>(mView) {
                    @Override
                    public void onNext(Articles articles) {
                        super.onNext(articles);
                        mView.showProjects(articles.getDatas());
                    }
                }));
    }

    @Override
    public void loadMoreProjects(int pageNum, int id) {
        addSubcriber(
                mModel.getProjects(pageNum, id)
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<Articles>(mView, false, false) {
                            @Override
                            public void onNext(Articles articles) {
                                super.onNext(articles);
                                mView.showMoreProjects(articles.getDatas());
                            }
                        }));
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
                        })
        );
    }
}
