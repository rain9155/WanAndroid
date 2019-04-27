package com.example.hy.wanandroid.base.activity;

import com.example.hy.wanandroid.base.presenter.BasePresenter;

/**
 * 封装了获取Presenter的Activity基类
 * Created by 陈健宇 at 2018/12/31
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    protected abstract T getPresenter();
    protected T mPresenter;

    @Override
    protected void initView() {
        mPresenter = getPresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
