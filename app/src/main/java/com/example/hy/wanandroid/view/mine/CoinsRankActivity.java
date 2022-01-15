package com.example.hy.wanandroid.view.mine;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CoinsRankAdapter;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.hy.wanandroid.contract.mine.CoinRankContract;
import com.example.hy.wanandroid.entity.UserCoin;
import com.example.hy.wanandroid.presenter.mine.CoinsRankPresenter;
import com.example.loading.Loading;
import com.example.loading.StatusView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class CoinsRankActivity extends BaseMvpActivity<CoinsRankPresenter> implements CoinRankContract.View {

    @BindView(R.id.tv_user_rank)
    TextView tvUserRank;
    @BindView(R.id.tv_my_rank)
    TextView tvMyRank;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_coin_rank)
    RecyclerView rvCoinRank;
    @BindView(R.id.srl_coin_rank)
    SwipeRefreshLayout srlCoinRank;
    @BindView(R.id.fbtn_up)
    FloatingActionButton upButton;

    @Inject
    CoinsRankPresenter mCoinsRankPresenter;
    @Inject
    CoinsRankAdapter mCoinsRankAdapter;

    private List<UserCoin> mCoinsRanks;
    private int mPageNum = 1;
    private boolean isLoadMore;
    private StatusView mStatusView;
    private boolean isOver;

    @Override
    protected CoinsRankPresenter getPresenter() {
        return mCoinsRankPresenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coins_rank;
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();

        toolbar.setNavigationOnClickListener(v -> finish());

        mCoinsRanks = mCoinsRankAdapter.getData();

        mStatusView = Loading.beginBuildStatusView(this)
                .warpView(rvCoinRank)
                .addErrorView(R.layout.error_view)
                .withReload(() -> reLoad(), R.id.cl_reload)
                .create();

        srlCoinRank.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        srlCoinRank.setOnRefreshListener( () -> {
            if(mCoinsRankAdapter.isLoading()){
                srlCoinRank.setRefreshing(false);
                return;
            }
            isLoadMore = false;
            mPageNum = 1;
            mPresenter.getUserRank();
            mPresenter.getCoinRanks(1);
        });
        srlCoinRank.setRefreshing(true);

        rvCoinRank.setHasFixedSize(true);
        rvCoinRank.setAdapter(mCoinsRankAdapter);
        rvCoinRank.setLayoutManager(new LinearLayoutManager(this));
        rvCoinRank.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        mCoinsRankAdapter.openLoadAnimation();
        mCoinsRankAdapter.setOnLoadMoreListener(() -> {
            if(srlCoinRank.isRefreshing()) {
                mCoinsRankAdapter.loadMoreComplete();
                return;
            }
            if(isOver){
                mCoinsRankAdapter.loadMoreEnd();
                showToast(getString(R.string.toast_noMoreData));
                return;
            }
            isLoadMore = true;
            mPageNum++;
            mPresenter.getCoinRanks(mPageNum);
        }, rvCoinRank);

        upButton.setOnClickListener(v -> rvCoinRank.smoothScrollToPosition(0));
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getUserRank();
        mPresenter.getCoinRanks(1);

    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CoinsRankActivity.class);
        context.startActivity(intent);
    }


    @Override
    public void showUserRank(UserCoin userCoin) {
        tvUserRank.setText(String.valueOf(userCoin.getRank()));
    }

    @Override
    public void showCoinRank(List<UserCoin> coinRanks, boolean isOver) {
        this.isOver = isOver;
        if(!isLoadMore){
            mCoinsRanks.clear();
        }
        mCoinsRanks.addAll(coinRanks);
        mCoinsRankAdapter.notifyDataSetChanged();
        if(isLoadMore){
            mCoinsRankAdapter.loadMoreComplete();
        }else {
            srlCoinRank.setRefreshing(false);
        }
        mStatusView.showSuccess();
    }

    @Override
    public void showErrorView() {
        srlCoinRank.setRefreshing(false);
        mCoinsRankAdapter.loadMoreComplete();
        mStatusView.showError();
    }


    @Override
    public void reLoad() {
        mPageNum = 1;
        isLoadMore = false;
        srlCoinRank.setRefreshing(true);
        mPresenter.getUserRank();
        mPresenter.getCoinRanks(1);
    }
}
