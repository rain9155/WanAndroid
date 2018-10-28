package com.example.hy.wanandroid.di.module;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.FirstHierarchyListAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.network.api.HierarchyApis;
import com.example.hy.wanandroid.network.api.HomeApis;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;
import com.example.hy.wanandroid.network.entity.homepager.Article;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * HierarchyFragment的Module
 * Created by 陈健宇 at 2018/10/28
 */
@Module
public class HierarchyFragmentModule {

    @Provides
    @PerFragment
    public HierarchyApis provideHomeApis(Retrofit retrofit){
        return retrofit.create(HierarchyApis.class);
    }

    @Provides
    @PerFragment
    List<FirstHierarchy> provideFirstHierarchyList(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    public FirstHierarchyListAdapter provideFirstHierarchyListAdapter(List<FirstHierarchy> firstHierarchyList){
        return new FirstHierarchyListAdapter(R.layout.item_hierarchy_first, firstHierarchyList);
    }

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }
}
