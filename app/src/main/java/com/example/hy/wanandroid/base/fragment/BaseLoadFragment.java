package com.example.hy.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.loading.Loading;
import com.example.loading.StatusView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 具有布局切换能力的Fragment
 * Created by 陈健宇 at 2018/11/4
 */
public abstract class BaseLoadFragment<T extends BasePresenter> extends BaseMvpFragment<T>{

    protected StatusView mStatusView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        View contentView = view.findViewById(R.id.normal_view);
        Loading.Builder builder = Loading.beginBuildStatusView(mActivity);
        if(contentView == null)
             builder.warpView(view);
        else
            builder.warpView(contentView);
        mStatusView = builder.withReload(() -> reLoad(), R.id.cl_reload).create();
        return contentView == null ? mStatusView.getWrappedView() : view;
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
    public void reLoad() {
    }

}
