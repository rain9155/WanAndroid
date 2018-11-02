package com.example.hy.wanandroid.contract.project;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.contract.MainContract;
import com.example.hy.wanandroid.network.entity.BaseResponse;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.project.Project;

import java.util.List;

import io.reactivex.Observable;

/**
 * 项目的Contract
 * Created by 陈健宇 at 2018/10/23
 */
public interface ProjectContract {

    interface View extends IView {
        void showProjectList(List<Project> projectList);
    }

    interface Presenter extends IPresenter<ProjectContract.View> {
        void loadProjectList();
    }

    interface Model{
        Observable<BaseResponse<List<Project>>> getProjectList();
    }
}
