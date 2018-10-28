package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.di.module.HierarchyFragmentModule;
import com.example.hy.wanandroid.di.module.HomeFragmentModule;
import com.example.hy.wanandroid.di.module.MainActivityModule;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.model.hierarchy.HierarchyModel;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;

import androidx.recyclerview.widget.LinearLayoutManager;
import dagger.Component;

/**
 * MainActivity的Component
 * Created by 陈健宇 at 2018/10/26
 */
@PerActivity
@Component( dependencies = AppComponent.class, modules = MainActivityModule.class)
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    HomeFragmentSubComponent getHomFragmentSubComponent(HomeFragmentModule homeFragmentModule);
    HierarchyFragmentSubComponent getHierarchyFragmentSubComponent(HierarchyFragmentModule hierarchyFragmentModule);
}
