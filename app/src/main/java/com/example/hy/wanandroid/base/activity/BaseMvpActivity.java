package com.example.hy.wanandroid.base.activity;

import android.content.Context;

import androidx.annotation.CallSuper;

import com.example.hy.wanandroid.utlis.LanguageUtil;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.App;

/**
 * 封装了获取Presenter的Activity基类
 * Created by 陈健宇 at 2018/12/31
 */
public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {

    protected abstract T getPresenter();
    protected T mPresenter;

    @Override
    protected void attachBaseContext(Context newBase) {
        String selectedLan = App.getContext().getAppComponent().getDataModel().getSelectedLanguage();
        super.attachBaseContext(LanguageUtil.attachBaseContext(newBase, selectedLan));
    }

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
    protected void initData() {
        if(mPresenter != null){
            mPresenter.subscribeEvent();
        }
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
