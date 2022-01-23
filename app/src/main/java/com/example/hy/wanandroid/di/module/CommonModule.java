package com.example.hy.wanandroid.di.module;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.hy.wanandroid.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.entity.HotKey;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 * 通用的Module
 * @author chenjianyu.rain@bytedance.com
 * @date 1/15/22
 */
@Module
public class CommonModule {

    private Context mContext;

    public CommonModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideApplicationContext(){
        return mContext.getApplicationContext();
    }

    @Provides
    List<Fragment> provideFragmentList(){
        return new ArrayList<>();
    }

    @Provides
    File provideCacheFile(){
        return new File(Constant.PATH_NET);
    }

    @Provides
    LinearLayoutManager provideVerticalLinearLayoutManager(){
        return new LinearLayoutManager(App.getContext());
    }

    @Provides
    StaggeredGridLayoutManager provideVerticalGridLayoutManagerWith2SpanCount(){
        return new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
    }

    @Provides
    List<String> provideStringList() {
        return new ArrayList<>();
    }

    @Provides
    List<Integer> provideIntegerList() {
        return new ArrayList<>();
    }

    @Provides
    List<HotKey> provideHotKeyList(){
        return new ArrayList<>();
    }

}
