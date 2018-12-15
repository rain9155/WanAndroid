package com.example.hy.wanandroid.presenter.project;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 项目Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter{


    @Inject
    public ProjectPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void loadProjectList() {
        addSubcriber(
                mModel.getProjectList()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<Tab>>(mView ) {
                    @Override
                    public void onNext(List<Tab> projects) {
                        super.onNext(projects);
                        mView.showProjectList(projects);
                    }
                }));
    }
}
