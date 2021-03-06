package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.adapter.HistoryAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.entity.HotKey;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * SearchActivity的Module
 * Created by 陈健宇 at 2018/11/2
 */
@Module
public class SearchActivityModule {

    @Provides
    @PerActivity
    List<Article>  provideResquestList(){
        return new ArrayList<>();
    }


    @Provides
    @PerActivity
    List<String>  provideHistories(){
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    List<HotKey>  provideHotKeyList(){
        return new ArrayList<>();
    }

    @Provides
    LinearLayoutManager  provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }


    @Provides
    ArticlesAdapter provideLinearArticlesAdapter(List<Article> articles){
        return new ArticlesAdapter(R.layout.item_acticles, articles);
    }

    @Provides
    HistoryAdapter provideHistoryAdapter(List<String> histories){
        return new HistoryAdapter(R.layout.item_search_history, histories);
    }
}
