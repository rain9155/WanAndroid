package com.example.hy.wanandroid.base.fragment;

import com.example.hy.wanandroid.base.presenter.IPresenter;

/**
 * Created by 陈健宇 at 2018/12/31
 */
public abstract class BaseMvpFragment<T extends IPresenter> extends BaseFragment {

    protected abstract T getPresenter();
    protected T mPresenter;

    @Override
    protected void initView() {
        mPresenter = getPresenter();
        mPresenter.attachView(this);
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
