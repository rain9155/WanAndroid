package com.example.hy.wanandroid.di.component.fragment;

import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.view.project.ProjectFragment;
import com.example.hy.wanandroid.view.project.ProjectsFragment;

import dagger.Subcomponent;

/**
 * ProjectFragment的Component
 * Created by 陈健宇 at 2018/10/29
 */
@Subcomponent(modules = ProjectFragmentModule.class)
public interface ProjectFragmentComponent {

    void inject(ProjectFragment projectFragment);

    void inject(ProjectsFragment projectsFragment);

}
