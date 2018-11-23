package com.example.hy.wanandroid.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CollectionsAdapter;
import com.example.hy.wanandroid.base.activity.BaseLoadActivity;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;
import com.example.hy.wanandroid.di.component.activity.DaggerCollectionActivityComponent;
import com.example.hy.wanandroid.presenter.mine.CollectionPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class CollectionActivity extends BaseLoadActivity implements CollectionContract.View {


    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_collections)
    RecyclerView rvCollections;
    @BindView(R.id.normal_view)
    SmartRefreshLayout normalView;

    @Inject
    CollectionPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    List<Collection> mCollections;
    @Inject
    CollectionsAdapter mCollectionsAdapter;

    private int pageNum = 0;//首页文章页数
    private boolean isLoadMore = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        if(!User.getInstance().isLoginStatus()) LoginActivity.startActivityForResult(this, Constant.REQUEST_SHOW_COLLECTIONS);

        DaggerCollectionActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);

        //标题
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.mineFragment_tvCollect);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());

        //collections
        mCollectionsAdapter.openLoadAnimation();
        rvCollections.setLayoutManager(mLinearLayoutManager);
        rvCollections.setAdapter(mCollectionsAdapter);
        mCollectionsAdapter.setEmptyView(R.layout.empty_view, normalView);
        mCollectionsAdapter.setOnItemClickListener((adapter, view, position) -> {
            Collection collection = mCollections.get(position);
            ArticleActivity.startActivity(CollectionActivity.this, collection.getLink(), collection.getTitle(), collection.getId(), true, false);
        });
        normalView.setOnRefreshListener(refreshLayout -> {
            isLoadMore = false;
            mPresenter.loadMoreCollections(0);
        });
        normalView.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            pageNum++;
            mPresenter.loadMoreCollections(pageNum);
        });
    }

    @Override
    protected void initData() {
        mPresenter.loadCollections(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && Constant.REQUEST_SHOW_COLLECTIONS == requestCode) mPresenter.loadCollections(0);
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void unableRefresh() {
        if(isLoadMore) normalView.finishLoadMore(); else normalView.finishRefresh();
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadCollections(0);
    }

    @Override
    public void showCollections(List<Collection> collections) {
        mCollections.addAll(collections);
        mCollectionsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreCollections(List<Collection> collections) {
        if (isLoadMore) {
            normalView.finishLoadMore();
        } else {
            if (!CommonUtil.isEmptyList(mCollections)) mCollections.clear();
            normalView.finishRefresh();
        }
        mCollections.addAll(collections);
        mCollectionsAdapter.notifyDataSetChanged();
    }

    public static void startActivity(Context context){
        Intent intent = new Intent(context, CollectionActivity.class);
        context.startActivity(intent);
    }
}
