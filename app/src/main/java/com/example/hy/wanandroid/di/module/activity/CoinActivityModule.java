package com.example.hy.wanandroid.di.module.activity;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CoinsAdapter;
import com.example.hy.wanandroid.adapter.CollectionsAdapter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.entity.Collection;
import com.example.loading.Loading;
import com.example.loading.StatusView;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * CoinActivity的Module
 * Created by 陈健宇 at 2019/9/21
 */
@Module
public class CoinActivityModule {

    @PerActivity
    @Provides
    List<Coin> provideCoins(){
        return new ArrayList<>();
    }

    @Provides
    StaggeredGridLayoutManager provideLayoutManager(){
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Provides
    @PerActivity
    CoinsAdapter provideLinearCollectionsAdapter(List<Coin> coinList){
        return new CoinsAdapter(R.layout.item_coins, coinList);
    }


}
