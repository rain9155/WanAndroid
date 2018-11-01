package com.example.hy.wanandroid.view.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.TagsAdapter;
import com.example.hy.wanandroid.adapter.TagsNameAdapter;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.di.component.activity.DaggerNavigationActivityComponent;
import com.example.hy.wanandroid.di.module.activity.NavigationActivityModule;
import com.example.hy.wanandroid.network.entity.navigation.Tag;
import com.example.hy.wanandroid.presenter.navigation.NavigationPresenter;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavigationActivity extends BaseActivity implements NavigationContract.View {

    @BindView(R.id.vtl_navigation)
    VerticalTabLayout vtlNavigation;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;

    @Inject
    NavigationPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    TagsAdapter mTagsAdapter;
    @Inject
    List<String> mTagsName;
    @Inject
    List<Tag> mTags;

    private TagsNameAdapter mTagsNameAdapter;
    private int index;//滑动停止时标签列表的下标

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DaggerNavigationActivityComponent.builder().appComponent(getAppComponent()).navigationActivityModule(new NavigationActivityModule()).build().inject(this);
        mPresenter.attachView(this);

        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setTitle(R.string.navigationActivity_tl_title);
        tlCommon.setNavigationOnClickListener(v -> finish() );

        mTagsAdapter.openLoadAnimation();
        rvNavigation.setLayoutManager(mLinearLayoutManager);
        rvNavigation.setAdapter(mTagsAdapter);

        vtlNavigation.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                rvNavigation.smoothScrollToPosition(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });

        rvNavigation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    index = mLinearLayoutManager.findFirstVisibleItemPosition();
                    vtlNavigation.setTabSelected(index);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

    }

    @Override
    protected void initData() {
        mPresenter.loadTags();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NavigationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showTags(List<Tag> tagList) {
        mTags.addAll(tagList);
        mTagsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTagsName(List<String> tagsName) {
        mTagsName.addAll(tagsName);
        mTagsNameAdapter = new TagsNameAdapter(this, mTagsName);
        vtlNavigation.setTabAdapter(mTagsNameAdapter);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
