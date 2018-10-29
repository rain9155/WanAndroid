package com.example.hy.wanandroid.di.module.fragment;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.FirstHierarchyAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerFragment;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Module;
import dagger.Provides;

/**
 * HierarchyFragment的Module
 * Created by 陈健宇 at 2018/10/28
 */
@Module
public class HierarchyFragmentModule {

    @Provides
    @PerFragment
    List<FirstHierarchy> provideFirstHierarchyList(){
        return new ArrayList<>();
    }

    @Provides
    @PerFragment
    public FirstHierarchyAdapter provideFirstHierarchyListAdapter(List<FirstHierarchy> firstHierarchyList){
        return new FirstHierarchyAdapter(R.layout.item_hierarchy_first, firstHierarchyList);
    }

    @Provides
    @PerFragment
    LinearLayoutManager provideLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }
}
