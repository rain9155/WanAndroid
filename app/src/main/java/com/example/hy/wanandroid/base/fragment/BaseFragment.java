package com.example.hy.wanandroid.base.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.commonlib.utils.ToastUtil;
import com.example.hy.wanandroid.config.App;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseFragment extends AbstractLazyLoadFragment
        implements BaseView {

    private Unbinder mUnbinder;
    protected Activity mActivity;
    protected View mView;
    protected abstract void inject();
    protected abstract void initView();//初始化控件
    protected abstract void loadData();//加载数据
    protected abstract int getLayoutId();//获取Fragment的布局Id

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        inject();
        initView();
        return view;
    }

    @Override
    protected void onLazyLoadData() {
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
    public void setStatusBarColor(boolean isSet){

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
    public void showTipsView(boolean isConnection) {
    }

    @Override
    public void showToast(String toast) {
        ToastUtil.toastInBottom(App.getContext(), toast);
    }

    @Override
    public void showToast(Activity activity, String toast) {
        ToastUtil.toastInBottom(activity, toast);
    }

    @Override
    public void showNormalView() {

    }

    @Override
    public void unableRefresh() {

    }

    @Override
    public void showDialog() {

    }

    @Override
    public void useNightNode(boolean isNight) {

    }

}
