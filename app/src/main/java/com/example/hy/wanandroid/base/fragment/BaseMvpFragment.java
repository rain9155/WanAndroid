package com.example.hy.wanandroid.base.fragment;

import androidx.annotation.CallSuper;

import com.example.hy.wanandroid.base.presenter.BasePresenter;

/**
 * Created by 陈健宇 at 2018/12/31
 */
public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment {

    protected abstract T getPresenter();
    protected T mPresenter;

    @Override
    @CallSuper
    protected void initView() {
        mPresenter = getPresenter();
        if(mPresenter != null){
            mPresenter.attachView(this);
        }
    }

    @Override
    @CallSuper
    protected void loadData() {
        if(mPresenter != null){
            mPresenter.subscribeEvent();
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

}
