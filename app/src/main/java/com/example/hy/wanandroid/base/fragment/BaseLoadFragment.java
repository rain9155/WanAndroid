package com.example.hy.wanandroid.base.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.hy.wanandroid.R;
import com.example.commonlib.utils.AnimUtil;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.hy.wanandroid.config.Constant.ERROR_STATE;
import static com.example.hy.wanandroid.config.Constant.LOADING_STATE;
import static com.example.hy.wanandroid.config.Constant.NORMAL_STATE;

/**
 * Created by 陈健宇 at 2018/11/4
 */
public abstract class BaseLoadFragment extends BaseFragment {

    private View mErrorView;
    private View mLoadingView;
    private ViewGroup mNormalView;
    private View mShowView;
    private View mHideView;
    private ImageView mIvReload;
    private ObjectAnimator mReloadAnimator;
    private int mCurrentState = NORMAL_STATE;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadView();
    }

    private void loadView() {
        if(mView == null) return;
        mNormalView = mView.findViewById(R.id.normal_view);
        if(mNormalView == null) throw new IllegalStateException("The subclass of BaseLoadFragment must contain a View it's id is named normal_view");
        if(!(mNormalView.getParent() instanceof ViewGroup)) throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup");

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(mActivity, R.layout.error_view, parent);
        View.inflate(mActivity, R.layout.loaging_view, parent);
        mErrorView = parent.findViewById(R.id.cl_reload);
        mLoadingView = parent.findViewById(R.id.rl_loading);
        mIvReload = mErrorView.findViewById(R.id.iv_reload);

        mErrorView.setOnClickListener(v -> reLoad());

        mNormalView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.INVISIBLE);
        mLoadingView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoading() {
        if(mCurrentState == LOADING_STATE) return;
        hideCurrentViewByState();
        mCurrentState = LOADING_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showErrorView() {
        if(mReloadAnimator != null) mReloadAnimator.cancel();
        if(mCurrentState == ERROR_STATE) return;
        hideCurrentViewByState();
        mCurrentState = ERROR_STATE;
        showCurrentViewByState();
    }

    @Override
    public void showNormalView() {
        if(mCurrentState == NORMAL_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NORMAL_STATE;
        showCurrentViewByState();
    }

    @Override
    public void reLoad() {
        startReloadAnimator();
    }

    @Override
    public void onStop() {
        if(mReloadAnimator != null) mReloadAnimator.cancel();
        super.onStop();
    }

    /**
     * 启动reload动画
     */
    @SuppressLint("WrongConstant")
    private void startReloadAnimator() {
        mReloadAnimator = ObjectAnimator.ofFloat(mIvReload, "rotation", 0, 360).setDuration(600);
        mReloadAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mReloadAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mReloadAnimator.setRepeatMode(ValueAnimator.INFINITE);
        mReloadAnimator.start();
    }

    /**
     * 显示当前布局根据mCurrentState
     */
    private void showCurrentViewByState() {
        switch (mCurrentState) {
            case NORMAL_STATE:
                mShowView = mNormalView;
                break;
            case LOADING_STATE:
                mShowView = mLoadingView;
                break;
            case ERROR_STATE:
                mShowView = mErrorView;
            default:
                break;
        }
        AnimUtil.showByAlpha(mShowView);
    }


    /**
     * 隐藏当前布局根据mCurrentState
     */
    private void hideCurrentViewByState() {
        switch (mCurrentState) {
            case NORMAL_STATE:
                mHideView = mNormalView;
                break;
            case LOADING_STATE:
                mHideView = mLoadingView;
                break;
            case ERROR_STATE:
                mHideView = mErrorView;
            default:
                break;
        }
        AnimUtil.hideByAlpha(mHideView);
    }

}
