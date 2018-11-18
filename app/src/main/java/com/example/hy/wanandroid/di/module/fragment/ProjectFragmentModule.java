package com.example.hy.wanandroid.di.module.fragment;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ProjectsAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * ProjectFragment的Module
 * Created by 陈健宇 at 2018/10/29
 */
@Module
public class ProjectFragmentModule {

    @Provides
    @PerFragment
    List<Fragment> provideSupportFragment(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    List<String> provideTitles(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    List<Integer> provideIds(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    List<Article> provideArticles(){
        return new ArrayList<>();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }

    @Provides
    ProjectsAdapter provideProjectsAdapter(List<Article> articles){
        return new ProjectsAdapter(R.layout.item_project_list, articles);
    }

}
