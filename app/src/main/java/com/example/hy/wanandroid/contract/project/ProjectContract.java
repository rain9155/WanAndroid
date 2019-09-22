package com.example.hy.wanandroid.contract.project;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Tab;

import java.util.List;

/**
 * 项目的Contract
 * Created by 陈健宇 at 2018/10/23
 */
public interface ProjectContract {

    interface View extends IView {
        void showProjectList(List<Tab> projectList);
    }

    interface Presenter {
        void loadProjectList();
    }

}
