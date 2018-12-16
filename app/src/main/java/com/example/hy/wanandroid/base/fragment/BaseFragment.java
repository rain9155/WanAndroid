package com.example.hy.wanandroid.base.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.component.NetWorkChangeReceiver;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.event.LoginEvent;
import com.example.hy.wanandroid.utils.ToastUtil;
import com.example.hy.wanandroid.view.mine.LoginActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Fragment的基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BaseFragment extends SupportFragment
        implements BaseView {

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
        ToastUtil.toastInBottom(_mActivity, toast);
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
