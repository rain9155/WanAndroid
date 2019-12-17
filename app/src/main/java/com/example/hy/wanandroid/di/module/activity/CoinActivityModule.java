package com.example.hy.wanandroid.di.module.activity;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CoinsAdapter;
import com.example.hy.wanandroid.di.scope.PerActivity;
import com.example.hy.wanandroid.entity.Coin;

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
