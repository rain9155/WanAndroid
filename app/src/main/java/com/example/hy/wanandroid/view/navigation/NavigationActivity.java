package com.example.hy.wanandroid.view.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.NavigationTagsAdapter;
import com.example.hy.wanandroid.adapter.NavigationTagsNameAdapter;
import com.example.hy.wanandroid.base.activity.BaseLoadActivity;
import com.example.hy.wanandroid.contract.navigation.NavigationContract;
import com.example.hy.wanandroid.entity.Tag;
import com.example.hy.wanandroid.presenter.navigation.NavigationPresenter;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.view.search.SearchActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

public class NavigationActivity extends BaseLoadActivity<NavigationPresenter> implements NavigationContract.View {

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

    private int mCurrentSelectedIndex;
    private boolean isDownScroll;//RecyclerView是否向下滑动状态
    private boolean isTagSelected;//标签是否被选中
    private List<Tag> mTags;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_navigation;
    }

    @Override
    protected NavigationPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView() {
        super.initView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        initToolBar();
        initVtlTabs();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mTags = mNavigationTagsAdapter.getData();
        mNavigationTagsAdapter.openLoadAnimation();
        rvNavigation.setLayoutManager(mLinearLayoutManager);
        rvNavigation.setAdapter(mNavigationTagsAdapter);
        rvNavigation.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    int firstIndex = mLinearLayoutManager.findFirstVisibleItemPosition();

                    //没有选中标签后，但是滚动RecyclerView了，需要同步，以第一个item设置给标签
                    if(!isTagSelected && vtlNavigation != null){
                        if(firstIndex != mCurrentSelectedIndex){
                            vtlNavigation.setTabSelected(firstIndex);
                            mCurrentSelectedIndex = firstIndex;
                        }
                    }
                    isTagSelected = false;

                    //标签被选中，并且RecyclerView向下滑动， 需要同步，被选中的item置顶
                    if(isDownScroll){
                        isDownScroll = false;
                        isTagSelected = true;
                        int indexInRecyclerView = mCurrentSelectedIndex - firstIndex;
                        //表示当前选中的位置在第一个可见item的下面
                        if(indexInRecyclerView >= 0 && indexInRecyclerView < recyclerView.getChildCount()){
                            View view = recyclerView.getChildAt(indexInRecyclerView);
                            recyclerView.smoothScrollBy(0, view.getTop());
                        }
                    }

                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void initVtlTabs() {
        //垂直标签栏
        vtlNavigation.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                isTagSelected = true;
                mCurrentSelectedIndex = position;
                rvNavigation.stopScroll();
                smoothScrollToPosition(position);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }

    /**
     * RecyclerView滑动
     */
    private void smoothScrollToPosition(int position) {
        int fistIndex = mLinearLayoutManager.findFirstVisibleItemPosition();
        int lastIndex = mLinearLayoutManager.findLastVisibleItemPosition();
        if(position < fistIndex)//RecyclerView准备向上滑动
        {
            rvNavigation.smoothScrollToPosition(position);
        } else if(position < lastIndex)//此处position处于屏幕中间，调用RecyclerView.smoothScrollToPosition(position)不会滚动，要手动smoothScroll向上滑
        {
            rvNavigation.smoothScrollBy(0, rvNavigation.getChildAt(mCurrentSelectedIndex - fistIndex).getTop());
        } else{//RecyclerView准备向下滑动
            isDownScroll = true;
            rvNavigation.smoothScrollToPosition(position);
        }
    }

    private void initToolBar() {
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.navigationActivity_tlTitle);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(this));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadTags();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, NavigationActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void showTags(List<Tag> tagList) {
        if(!CommonUtil.isEmptyList(mTags)) {
            mTags.clear();
        }
        mTags.addAll(tagList);
        mNavigationTagsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showTagsName(List<String> tagsName) {
        if(!CommonUtil.isEmptyList(mTagsName)) {
            mTagsName.clear();
        }
        mTagsName.addAll(tagsName);
        NavigationTagsNameAdapter navigationTagsNameAdapter = new NavigationTagsNameAdapter(this, mTagsName);
        vtlNavigation.setTabAdapter(navigationTagsNameAdapter);
    }

    @Override
    public void reLoad() {
        mPresenter.loadTags();
    }

}
