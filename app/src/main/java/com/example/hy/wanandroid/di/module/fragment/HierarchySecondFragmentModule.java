package com.example.hy.wanandroid.di.module.fragment;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * HierarchySecond的Fragment
 * Created by 陈健宇 at 2018/10/29
 */
@Module
public class HierarchySecondFragmentModule {

    @Provides
    @PerFragment
    List<Article> provideArticles(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    ArticlesAdapter provideArticlesAdapter(List<Article> articles){
        return new ArticlesAdapter(R.layout.item_home_acticles, articles, App.getContext());
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }

}
