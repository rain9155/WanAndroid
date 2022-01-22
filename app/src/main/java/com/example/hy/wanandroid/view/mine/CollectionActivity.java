package com.example.hy.wanandroid.view.mine;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CollectionAdapter;
import com.example.hy.wanandroid.base.activity.BaseLoadActivity;
import com.example.hy.wanandroid.entity.ArticleBean;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.contract.mine.CollectionContract;
import com.example.hy.wanandroid.entity.Collection;
import com.example.hy.wanandroid.event.CollectionEvent;
import com.example.hy.wanandroid.presenter.mine.CollectionPresenter;
import com.example.hy.wanandroid.utlis.AnimUtil;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.example.hy.wanandroid.widget.popup.PressPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dagger.Lazy;

public class CollectionActivity extends BaseLoadActivity<CollectionPresenter> implements CollectionContract.View {


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
    @BindView(R.id.tv_empty)
    TextView tvEmpty;

    @Inject
    CollectionPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    CollectionAdapter mCollectionAdapter;
    @Inject
    List<Integer> mIds;
    @Inject
    Lazy<PressPopupWindow> mPopupWindow;

    private int pageNum = 0;//首页文章页数
    private boolean isLoadMore = false;
    private int mCollectionPosition = -1;//点击的位置
    private Collection mCollection;
    private List<Collection> mCollections;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutId(){
        return R.layout.activity_collection;
    }

    @Override
    protected CollectionPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView( ) {
        super.initView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        }
        initToolBar();
        initRecyclerView();
        initRefreshView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView() {
        mCollections = mCollectionAdapter.getData();
        //collections
        mCollectionAdapter.openLoadAnimation();
        rvCollections.setLayoutManager(mLinearLayoutManager);
        rvCollections.setAdapter(mCollectionAdapter);
        mCollectionAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            mCollectionPosition = position;
            ArticleBean articleBean = new ArticleBean(mCollections.get(position));
            ArticleActivity.startActivity(this, articleBean, true);
        });
        mCollectionAdapter.setOnItemChildClickListener((adapter, view, position) -> {//取消收藏
            mCollectionPosition = position;
            mCollection = mCollections.get(position);
            mPresenter.unCollectArticle(mCollection.getId(), mCollection.getOriginId());
            AnimUtil.scale(view, -1);
        });
        mCollectionAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            Collection collection = mCollections.get(position);
            mPopupWindow.get().show(tlCommon, view, collection);
            return true;
        });
    }

    private void initRefreshView() {
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

    private void initToolBar() {
        //标题
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(R.string.mineFragment_tvCollect);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> {
            if(!CommonUtil.isEmptyList(mIds)) {
                RxBus.getInstance().post(new CollectionEvent(mIds));
            }
            finish();
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadCollections(0);
    }

    @Override
    public void onBackPressed() {
        if(!CommonUtil.isEmptyList(mIds)) {
            RxBus.getInstance().post(new CollectionEvent(mIds));
        }
        finish();
    }

    @Override
    public void unableRefresh() {
        if (isLoadMore) {
            normalView.finishLoadMore();
        } else {
            normalView.finishRefresh();
        }
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadCollections(0);
    }

    @Override
    public void showCollections(List<Collection> collections) {
        if(!CommonUtil.isEmptyList(mCollections)) {
            mCollections.clear();
        }
        mCollections.addAll(collections);
        mCollectionAdapter.notifyDataSetChanged();
        if(CommonUtil.isEmptyList(mCollections)) {
            showEmptyLayout();
        }
    }

    @Override
    public void showMoreCollections(List<Collection> collections) {
        if (isLoadMore) {
            normalView.finishLoadMore();
        } else {
            if (!CommonUtil.isEmptyList(mCollections)) {
                mCollections.clear();
            }
            normalView.finishRefresh();
        }
        mCollections.addAll(collections);
        mCollectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.toast_uncollection_success));
        mIds.add(mCollections.get(mCollectionPosition).getOriginId());
        mCollections.remove(mCollectionPosition);
        mCollectionAdapter.notifyItemRemoved(mCollectionPosition + mCollectionAdapter.getHeaderLayoutCount());
        if(CommonUtil.isEmptyList(mCollections)) {
            showEmptyLayout();
        }
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
