package com.example.hy.wanandroid.model.project;

import com.example.hy.wanandroid.contract.project.ProjectsContract;
import com.example.hy.wanandroid.core.network.api.ProjectApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.homepager.Articles;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 具体项目分类的model
 * Created by 陈健宇 at 2018/10/30
 */
public class ProjectsModel implements ProjectsContract.Model {

    private ProjectApis mProjectApis;

    @Inject
    public ProjectsModel(ProjectApis projectApis) {
        mProjectApis = projectApis;
    }

    @Override
    public Observable<BaseResponse<Articles>> getProjects(int pageNum, int id) {
        return mProjectApis.getProjects(pageNum, id);
    }

}
