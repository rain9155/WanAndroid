package com.example.hy.wanandroid.di.module.activity;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.Per2Activity;
import com.example.hy.wanandroid.network.entity.homepager.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * HierarchySecondActivity的Module
 * Created by 陈健宇 at 2018/10/29
 */
@Module
public class HierarchySecondActivityModule {

    @Provides
    List<SupportFragment> provideSupportFragment(){
        return new ArrayList<>();
    }

    @Provides
    List<String> provideTitles(){
        return new ArrayList<>();
    }

    @Provides
    List<Integer> provideIds(){
        return new ArrayList<>();
    }

}
