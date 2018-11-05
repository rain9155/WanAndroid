package com.example.hy.wanandroid.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.utils.SnackUtil;
import com.example.hy.wanandroid.utils.ToastUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseFragment extends SupportFragment
        implements IView {

    private Unbinder mUnbinder;

    protected abstract void initView();//初始化控件
    protected abstract void loadData();//加载数据
    protected abstract int getLayoutId();//获取Fragment的布局Id

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        loadData();
    }

    @Override
    public void onDestroy() {
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
    public void showToast(String toast) {
        ToastUtil.showToast(_mActivity, toast);
    }

    @Override
    public void showSnackBar(String toast) {
        SnackUtil.showSnackBar(_mActivity, toast);
    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void unableRefresh() {

    }
}
