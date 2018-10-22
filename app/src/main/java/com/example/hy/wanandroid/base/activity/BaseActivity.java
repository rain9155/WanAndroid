package com.example.hy.wanandroid.base.activity;

import android.os.Bundle;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation_swipeback.SwipeBackActivity;

/**
 * Activity的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseActivity<T extends IPresenter> extends SwipeBackActivity
        implements IView {

    @Inject
    protected T mPresenter;
    private Unbinder mUnbinder;

    protected abstract int getLayoutId();//获取Activity的布局Id
    protected abstract int initView();//初始化控件
    protected abstract int initData();//初始化数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if(mPresenter != null) mPresenter.attachView(this);
        mUnbinder = ButterKnife.bind(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        if(mUnbinder != null && mUnbinder != Unbinder.EMPTY){
            mUnbinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void showErrorView() {

    }

    @Override
    public void showErrorMes() {

    }

    @Override
    public void reLoad() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showToast() {

    }

    @Override
    public void showSnackBar() {

    }
}
