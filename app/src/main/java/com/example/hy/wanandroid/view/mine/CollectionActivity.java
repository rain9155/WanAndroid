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
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.core.network.entity.mine.Collection;
import com.example.hy.wanandroid.di.component.activity.DaggerCollectionActivityComponent;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.presenter.mine.CollectionPresenter;
import com.example.hy.wanandroid.utils.AnimUtil;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    private int pageNum = 0;//首页文章页数
    private boolean isLoadMore = false;
    private int mCollectionPosition = -1;//点击的位置

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collection;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);

        if (!User.getInstance().isLoginStatus())
            LoginActivity.startActivityForResult(this, Constant.REQUEST_SHOW_COLLECTIONS);

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
        mCollectionsAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            mCollectionPosition = position;
            Collection collection = mCollections.get(position);
            ArticleActivity.startActivity(this, collection.getLink(), collection.getTitle(), collection.getId(), true, true);
        });
        mCollectionsAdapter.setOnItemChildClickListener((adapter, view, position) -> {//取消收藏
            mCollectionPosition = position;
            Collection collection = mCollections.get(position);
            mPresenter.unCollectArticle(collection.getId(), collection.getOriginId());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_CANCELED)
            finish();
        if (resultCode == RESULT_OK && requestCode == Constant.REQUEST_SHOW_COLLECTIONS)
            mPresenter.loadCollections(0);
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    @Override
    public void unableRefresh() {
        if (isLoadMore) normalView.finishLoadMore();
        else normalView.finishRefresh();
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
        if(CommonUtil.isEmptyList(mCollections)) showEmptyLayout();
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

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.common_uncollection_success));
        mCollections.remove(mCollectionPosition);
        mCollectionsAdapter.notifyItemRemoved(mCollectionPosition + mCollectionsAdapter.getHeaderLayoutCount());
        RxBus.getInstance().post(new CollectionEvent(mCollections.get(mCollectionPosition).getOriginId()));
        if(CommonUtil.isEmptyList(mCollections)) showEmptyLayout();
    }

    @Override
    public void showEmptyLayout() {
        AnimUtil.showByAlpha(tvEmpty);
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CollectionActivity.class);
        context.startActivity(intent);
    }

}
