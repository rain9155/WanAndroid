package com.example.hy.wanandroid.model.project;

import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.core.network.api.ProjectApis;
import com.example.hy.wanandroid.core.network.entity.BaseResponse;
import com.example.hy.wanandroid.core.network.entity.project.Project;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * 项目tab的model
 * Created by 陈健宇 at 2018/10/29
 */
public class ProjectModel implements ProjectContract.Model {


    private ProjectApis mProjectApis;

    @Inject
    public ProjectModel(ProjectApis projectApis) {
        mProjectApis = projectApis;
    }

    @Override
    public Observable<BaseResponse<List<Project>>> getProjectList() {
        return mProjectApis.getProjectList();
    }
}
