package com.example.hy.wanandroid.base.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.commonlib.utils.LogUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * Create by 陈健宇 at 2018/7/15
 */
public abstract class AbstractLazyLoadFragment extends Fragment {

    private boolean isViewCreated = false;//布局是否被创建
    private boolean isLoadData = false;//数据是否被加载
    private String TAG = "baselazy";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        LogUtil.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG, "onCreate()");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        LogUtil.d(TAG, "onViewCreated()");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(isViewCreated && getUserVisibleHint()){
            onLazyLoadData();
            isLoadData = true;
        }
        LogUtil.d(TAG, "onActivityCreated()");
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isViewCreated)
            onLazyLoadData();
        LogUtil.d(TAG, "setUserVisibleHint()");

    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.d(TAG, "onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG, "onDestroy()");
    }

    protected void onLazyLoadData(){}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewCreated = false;
        isLoadData = false;
        LogUtil.d(TAG, "onDestroyView()");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.d(TAG, "onHiddenChanged()");
    }
}
