package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CollectionsAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;
import com.example.hy.wanandroid.di.scope.PerActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * CollectionActivity的Module
 * Created by 陈健宇 at 2018/11/22
 */
@Module
public class CollectionActivityModule {

    @PerActivity
    @Provides
    List<Collection> provideCollections(){
        return new ArrayList<>();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }

    @Provides
    @PerActivity
    CollectionsAdapter provideLinearCollectionsAdapter(List<Collection> collections){
        return new CollectionsAdapter(R.layout.item_home_acticles, collections);
    }
}
