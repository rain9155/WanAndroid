package com.example.hy.wanandroid.base.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.IView;
import com.jaeger.library.StatusBarUtil;

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
    protected abstract void initView();//初始化控件
    protected abstract void initData();//初始化数据

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        if(mPresenter != null) mPresenter.attachView(this);
        mUnbinder = ButterKnife.bind(this);
        setStatusBarColor();
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

    protected void setStatusBarColor(){
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
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
