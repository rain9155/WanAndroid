package com.example.hy.wanandroid.base.activity;

import android.view.ViewGroup;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.loading.Loading;
import com.example.loading.StatusView;

import android.view.View;

/**
 * 具有布局切换能力的Activity
 * Created by 陈健宇 at 2018/11/4
 */
public abstract class BaseLoadActivity<T extends BasePresenter> extends BaseMvpActivity<T>{

    private StatusView mStatusView;

    @Override
    protected void initView() {
        super.initView();
        View contentView = findViewById(R.id.normal_view);
        Loading.Builder builder = Loading.beginBuildStatusView(this);
        if(contentView == null)
            builder.warpActivity(this);
        else
            builder.warpView(contentView);
        mStatusView = builder.withReload(() -> reLoad(), R.id.cl_reload).create();
    }

    @Override
    public void showLoading() {
        mStatusView.showLoading();
    }

    @Override
    public void showErrorView() {
        mStatusView.showError();
    }

    @Override
    public void showNormalView() {
        mStatusView.showSuccess();
    }

    @Override
    public void showEmptyView() {
        mStatusView.showEmpty();
    }

    @Override
    public void reLoad() {
    }

}
