package com.example.hy.wanandroid.base.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.utils.AnimUtil;

import static com.example.hy.wanandroid.config.Constant.ERROR_STATE;
import static com.example.hy.wanandroid.config.Constant.LOADING_STATE;
import static com.example.hy.wanandroid.config.Constant.NORMAL_STATE;

/**
 * Created by 陈健宇 at 2018/11/4
 */
public abstract  class BaseLoadActivity extends BaseActivity {

    private View mErrorView;
    private View mLoadingView;
    private ViewGroup mNormalView;
    private ImageView mIvReload;
    private ObjectAnimator mReloadAnimator;
    private int mCurrentState = NORMAL_STATE;

    @Override
    protected void initView(Bundle savedInstanceState) {

        mNormalView =  findViewById(R.id.normal_view);
        if(mNormalView == null) throw new IllegalStateException("The subclass of BaseLoadActivity must contain a View it's id is named normal_view");
        if(!(mNormalView.getParent() instanceof ViewGroup)) throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup");

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(this, R.layout.error_view, parent);//把错误布局加载进正常布局的父布局中
        View.inflate(this, R.layout.loaging_view, parent);//把加载布局加载进正常布局的父布局中
        mErrorView = parent.findViewById(R.id.cl_reload);//从父布局找到错误布局实例
        mLoadingView = parent.findViewById(R.id.rl_loading);//从父布局找到加载布局实例
        mIvReload = mErrorView.findViewById(R.id.iv_reload);

        mErrorView.setOnClickListener(v -> reLoad());

        //初始化View状态
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
        super.reLoad();
    }

    @Override
    protected void onDestroy() {
        if(mReloadAnimator != null) mReloadAnimator.cancel();
        super.onDestroy();
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
        View showView = null;
        switch (mCurrentState) {
            case NORMAL_STATE:
                showView = mNormalView;
                break;
            case LOADING_STATE:
                showView = mLoadingView;
                break;
            case ERROR_STATE:
                showView = mErrorView;
            default:
                break;
        }
        if(showView == null) return;
        AnimUtil.showByAlpha(showView);
    }


    /**
     * 隐藏当前布局根据mCurrentState
     */
    private void hideCurrentViewByState() {
        View hideView = null;
        switch (mCurrentState) {
            case NORMAL_STATE:
                hideView = mNormalView;
                break;
            case LOADING_STATE:
                hideView = mLoadingView;
                break;
            case ERROR_STATE:
                hideView = mErrorView;
            default:
                break;
        }
        if(hideView == null) return;
        AnimUtil.hideByAlpha(hideView);
    }
}
