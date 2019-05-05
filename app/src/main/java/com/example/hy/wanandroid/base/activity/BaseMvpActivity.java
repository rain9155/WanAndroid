package com.example.hy.wanandroid.base.activity;

import android.content.Context;

import com.example.commonlib.utils.LanguageUtil;
import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.component.AppComponent;

import java.util.Locale;

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
