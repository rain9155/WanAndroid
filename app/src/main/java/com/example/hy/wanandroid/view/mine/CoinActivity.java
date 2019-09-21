package com.example.hy.wanandroid.view.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.animation.BaseAnimation;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.CoinsAdapter;
import com.example.hy.wanandroid.base.activity.BaseMvpActivity;
import com.example.hy.wanandroid.contract.mine.CoinContract;
import com.example.hy.wanandroid.di.component.activity.DaggerCoinActivityComponent;
import com.example.hy.wanandroid.di.module.activity.CoinActivityModule;
import com.example.hy.wanandroid.entity.Coin;
import com.example.hy.wanandroid.entity.UserCoin;
import com.example.hy.wanandroid.presenter.mine.CoinPresenter;
import com.example.loading.Loading;
import com.example.loading.StatusView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoinActivity extends BaseMvpActivity<CoinPresenter> implements CoinContract.View {

    @Inject
    CoinPresenter mPresenter;
    @Inject
    List<Coin> mCoinList;
    @Inject
    StaggeredGridLayoutManager mLayoutManager;
    @Inject
    CoinsAdapter mCoinsAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tv_user_coin)
    TextView tvUserCoin;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.rv_coin)
    RecyclerView rvCoin;
    @BindView(R.id.srl_coin)
    SwipeRefreshLayout srlCoin;

    private boolean isOver;
    private int mPageNum = 1;
    private boolean isLoadMore;
    @Inject
    StatusView mStatusView;


    @Override
    protected void inject() {
        DaggerCoinActivityComponent.builder()
                .appComponent(getAppComponent())
                .coinActivityModule(new CoinActivityModule(this, srlCoin))
                .build()
                .inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coin;
    }

    @Override
    protected CoinPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView() {
        super.initView();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        srlCoin.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark);
        srlCoin.setOnRefreshListener(() -> {
            isLoadMore = false;
            mPageNum = 1;
            mPresenter.getUserCoin();
            mPresenter.getCoins(mPageNum);
        });
        srlCoin.setRefreshing(true);

        rvCoin.setHasFixedSize(true);
        rvCoin.setLayoutManager(mLayoutManager);
        rvCoin.setAdapter(mCoinsAdapter);
        mCoinsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        mCoinsAdapter.setOnLoadMoreListener(() -> {
            if(isOver) {
                showToast(getString(R.string.toast_noMoreData));
                mCoinsAdapter.loadMoreComplete();
                return;
            }
            isLoadMore = true;
            mPageNum++;
            mPresenter.getCoins(mPageNum);

        }, rvCoin);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getUserCoin();
        mPresenter.getCoins(1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showUserCoin(UserCoin userCoin) {
        tvUserCoin.setText(String.valueOf(userCoin.getCoinCount()));
    }

    @Override
    public void showCoins(List<Coin> coinList, boolean over) {
        isOver = over;
        if(!isLoadMore){
            mCoinList.clear();
        }
        mCoinList.addAll(coinList);
        mCoinsAdapter.notifyDataSetChanged();
        mCoinsAdapter.disableLoadMoreIfNotFullPage(rvCoin);
        srlCoin.setRefreshing(false);
        mCoinsAdapter.loadMoreComplete();
        mStatusView.showSuccess();
    }

    @Override
    public void showCoinsFail() {
        srlCoin.setRefreshing(false);
        mCoinsAdapter.loadMoreComplete();
        mStatusView.showError();
    }

    @Override
    public void reLoad() {
        isLoadMore = false;
        srlCoin.setRefreshing(true);
        mPresenter.getUserCoin();
        mPresenter.getCoins(1);
    }

    public static void startActivity(Context context) {
        context.startActivity(new Intent(context, CoinActivity.class));
    }

}
