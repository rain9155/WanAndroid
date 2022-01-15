package com.example.hy.wanandroid.di.component;

import com.example.hy.wanandroid.di.module.CommonModule;
import com.example.hy.wanandroid.di.module.NetworkModule;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.hierarchy.HierarchyFragment;
import com.example.hy.wanandroid.view.hierarchy.HierarchySecondActivity;
import com.example.hy.wanandroid.view.hierarchy.HierarchySecondFragment;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.example.hy.wanandroid.view.homepager.HomeFragment;
import com.example.hy.wanandroid.view.mine.CoinsActivity;
import com.example.hy.wanandroid.view.mine.CoinsRankActivity;
import com.example.hy.wanandroid.view.mine.CollectionActivity;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.example.hy.wanandroid.view.mine.MineFragment;
import com.example.hy.wanandroid.view.mine.RegisterActivity;
import com.example.hy.wanandroid.view.mine.SettingsActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.project.ProjectFragment;
import com.example.hy.wanandroid.view.project.ProjectsFragment;
import com.example.hy.wanandroid.view.search.SearchActivity;
import com.example.hy.wanandroid.view.wechat.WeChatFragment;
import com.example.hy.wanandroid.view.wechat.WeChatsFragment;

import javax.inject.Singleton;
import dagger.Component;

/**
 * App的component:
 * <a herf="https://developer.android.com/training/dependency-injection/dagger-android">Using Dagger in Android apps</>
 * <a herf="https://dagger.dev/dev-guide/">Dagger Guide</a>
 * Created by 陈健宇 at 2018/10/26
 */
@Singleton
@Component(modules = {NetworkModule.class, CommonModule.class})
public interface AppComponent {

    void inject(MainActivity mainActivity);

    void inject(NavigationActivity navigationActivity);

    void inject(SearchActivity searchActivity);

    void inject(HierarchySecondActivity hierarchySecondActivity);

    void inject(LoginActivity loginActivity);

    void inject(RegisterActivity registerActivity);

    void inject(ArticleActivity articleActivity);

    void inject(CoinsActivity coinActivity);

    void inject(CollectionActivity collectionActivity);

    void inject(SettingsActivity settingsActivity);

    void inject(CoinsRankActivity coinsRankActivity);

    void inject(HomeFragment homeFragment);

    void inject(HierarchyFragment hierarchyFragment);

    void inject(HierarchySecondFragment hierarchySecondFragment);

    void inject(MineFragment mineFragment);

    void inject(ProjectFragment projectFragment);

    void inject(ProjectsFragment projectsFragment);

    void inject(WeChatFragment weChatFragment);

    void inject(WeChatsFragment weChatsFragment);

    DataModel getDataModel();

}
