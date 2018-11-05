package com.example.hy.wanandroid.presenter.project;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.model.project.ProjectModel;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.DefaultObserver;
import com.example.hy.wanandroid.network.entity.project.Project;
import com.example.hy.wanandroid.utils.RxUtils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 项目Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter{


    private ProjectContract.Model mProjectModel;

    @Inject
    public ProjectPresenter(ProjectModel projectModel) {
        mProjectModel = projectModel;
    }

    @Override
    public void loadProjectList() {
        addSubcriber(
                mProjectModel.getProjectList()
                .compose(RxUtils.switchSchedulers())
                .compose(RxUtils.handleRequest2())
                .subscribeWith(new DefaultObserver<List<Project>>(mView ) {
                    @Override
                    public void onNext(List<Project> projects) {
                        super.onNext(projects);
                        mView.showProjectList(projects);
                    }
                }));
    }
}
