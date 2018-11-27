package com.example.hy.wanandroid.view.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.NavigationTagsAdapter;
import com.example.hy.wanandroid.adapter.NavigationTagsNameAdapter;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.base.activity.BaseLoadActivity;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.di.component.activity.DaggerNavigationActivityComponent;
import com.example.hy.wanandroid.di.module.activity.NavigationActivityModule;
import com.example.hy.wanandroid.model.network.entity.navigation.Tag;
import com.example.hy.wanandroid.presenter.navigation.NavigationPresenter;
import com.example.hy.wanandroid.view.search.SearchActivity;

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

public class NavigationActivity extends BaseLoadActivity implements NavigationContract.View {

    @BindView(R.id.vtl_navigation)
    VerticalTabLayout vtlNavigation;
    @BindView(R.id.rv_navigation)
    RecyclerView rvNavigation;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;

    @Inject
    NavigationPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    NavigationTagsAdapter mNavigationTagsAdapter;
    @Inject
    List<String> mTagsName;
    @Inject
    List<Tag> mTags;

    private NavigationTagsNameAdapter mNavigationTagsNameAdapter;
    private int index;//滑动停止时标签列表的下标

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected void initView() {
        super.initView();
        DaggerNavigationActivityComponent.builder().appComponent(getAppComponent()).navigationActivityModule(new NavigationActivityModule()).build().inject(this);
        mPresenter.attachView(this);

        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.navigationActivity_tlTitle);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(this));

        //垂直子标签栏
        mNavigationTagsAdapter.openLoadAnimation();
        rvNavigation.setLayoutManager(mLinearLayoutManager);
        rvNavigation.setAdapter(mNavigationTagsAdapter);

        //垂直标签栏
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
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
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
        mNavigationTagsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTagsName(List<String> tagsName) {
        mTagsName.addAll(tagsName);
        mNavigationTagsNameAdapter = new NavigationTagsNameAdapter(this, mTagsName);
        vtlNavigation.setTabAdapter(mNavigationTagsNameAdapter);
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadTags();
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
