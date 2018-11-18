package com.example.hy.wanandroid.presenter.project;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.project.ProjectsContract;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.model.project.ProjectsModel;
import com.example.hy.wanandroid.core.network.entity.DefaultObserver;
import com.example.hy.wanandroid.core.network.entity.homepager.Articles;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

/**
 * 详细项目分类的Presenter
 * Created by 陈健宇 at 2018/10/30
 */
public class ProjectsPresenter extends BasePresenter<ProjectsContract.View> implements ProjectsContract.Presenter {

    private ProjectsContract.Model mProjectsModel;

    @Inject
    public ProjectsPresenter(ProjectsModel projectsModel) {
        mProjectsModel = projectsModel;
    }

    @Override
    public void subscribleEvent() {
        super.subscribleEvent();
        addSubcriber(
                RxBus.getInstance().toObservable(ToppingEvent.class)
                .subscribe(toppingEvent -> mView.topping())
        );
    }

    @Override
    public void loadProjects(int pageNum, int id) {
        addSubcriber(
                mProjectsModel.getProjects(pageNum, id)
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
                mProjectsModel.getProjects(pageNum, id)
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
}
