package com.example.hy.wanandroid.base.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.hy.wanandroid.R;

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
    private ImageView mIvReload;
    private ObjectAnimator mReloadAnimator;
    private int mCurrentState = NORMAL_STATE;

    @Override
    protected void loadData() {
        if(getView() == null) return;
        mNormalView = getView().findViewById(R.id.normal_view);
        if(mNormalView == null) throw new IllegalStateException("The subclass of BaseLoadFragment must contain a View it's id is named normal_view");
        if(!(mNormalView.getParent() instanceof ViewGroup)) throw new IllegalStateException("mNormalView's ParentView should be a ViewGroup");

        ViewGroup parent = (ViewGroup) mNormalView.getParent();
        View.inflate(_mActivity, R.layout.error_view, parent);
        View.inflate(_mActivity, R.layout.loaging_view, parent);
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
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showErrorView() {
        if(mReloadAnimator != null) mReloadAnimator.cancel();
        if(mCurrentState == ERROR_STATE) return;
        hideCurrentViewByState();
        mCurrentState = ERROR_STATE;
        mErrorView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNormalView() {
        if(mCurrentState == NORMAL_STATE) return;
        hideCurrentViewByState();
        mCurrentState = NORMAL_STATE;
        mNormalView.setVisibility(View.VISIBLE);
    }

    @Override
    public void reLoad() {
        startReloadAnimator();
    }



    @Override
    public void onDestroy() {
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
     * 隐藏当前布局根据mCurrentState
     */
    private void hideCurrentViewByState() {
        switch (mCurrentState) {
            case NORMAL_STATE:
                if(mReloadAnimator != null) mReloadAnimator.cancel();
                mNormalView.setVisibility(View.INVISIBLE);
                break;
            case LOADING_STATE:
                if(mReloadAnimator != null) mReloadAnimator.cancel();
                mLoadingView.setVisibility(View.INVISIBLE);
                break;
            case ERROR_STATE:
                mErrorView.setVisibility(View.INVISIBLE);
            default:
                break;
        }
    }

}
