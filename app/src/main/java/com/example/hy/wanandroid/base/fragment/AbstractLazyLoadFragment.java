package com.example.hy.wanandroid.base.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.commonlib.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Create by 陈健宇 at 2018/7/15
 */
public abstract class AbstractLazyLoadFragment extends Fragment {

    private boolean isViewCreated = false;//布局是否被创建
    private boolean isLoadData = false;//数据是否加载
    private String TAG = "baselazy";

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        isViewCreated = true;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG, "onActivityCreated: " + String.valueOf(this.isHidden()));
        if(isViewCreated && getUserVisibleHint()){
            onLazyLoadData();
            isLoadData = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG, "setUserVisibleHint: " + String.valueOf(this.isHidden()));
        if(isVisibleToUser && isViewCreated && !isLoadData){
            onLazyLoadData();
            isLoadData = true;
        }
    }

    protected void onLazyLoadData(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoadData = false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }
}
