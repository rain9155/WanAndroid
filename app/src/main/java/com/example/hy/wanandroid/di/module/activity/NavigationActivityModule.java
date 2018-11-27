package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.NavigationTagsAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.model.network.entity.navigation.Tag;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * NavigationActivity的Model
 * Created by 陈健宇 at 2018/10/31
 */
@Module
public class NavigationActivityModule {

    @Provides
    @PerActivity
    List<String> provideTagsName(){
        return new ArrayList<>();
    }

    @Provides
    @PerActivity
    List<Tag> provideTags(){
        return new ArrayList<>();
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }

    @Provides
    NavigationTagsAdapter provideTagsAdapter(List<Tag> tags){
        return new NavigationTagsAdapter(R.layout.item_navigation_tabs, tags);
    }
}
