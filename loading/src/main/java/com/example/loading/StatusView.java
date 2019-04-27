package com.example.loading;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.commonlib.utils.AnimUtil;

/**
 * 一种可以根据状态切换布局视图的View
 * Created by 陈健宇 at 2019/4/25
 */
public class StatusView extends RelativeLayout {

    private static final String TAG = "StatusView";

    public static final int STATUS_SUCCESS = 0X00;
    public static final int STATUS_LOADING = 0X01;
    public static final int STATUS_ERROR = 0X02;
    public static final int STATUS_EMPTY = 0x03;
    private static int mCurrentStatus = STATUS_SUCCESS;

    protected View loadingView;
    protected View contentView;
    protected View errorView;
    protected View emptyView;
    protected View.OnClickListener reloadClick;
    protected View wrappedView;
    private static final RelativeLayout.LayoutParams DEFAULT_LP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

    public StatusView(Context context) {
        super(context, null);
    }

    public StatusView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public StatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(reloadClick != null) reloadClick = null;
        clear(contentView, loadingView, emptyView, errorView);
    }

    public void showSuccess(){
        showView(STATUS_SUCCESS);
    }

    public void showLoading(){
        showView(STATUS_LOADING);
    }

    public void showError(){
        showView(STATUS_ERROR);
    }

    public void showEmpty(){
        showView(STATUS_EMPTY);
    }

    public View getWrappedView() {
        return wrappedView;
    }

    /**
     * 根据status显示View视图
     * @param status 将要显示的View视图的status
     */
    private void showView(int status){
        if(Looper.myLooper() == Looper.getMainLooper()){
            changeViewByStatus(status);
        }else {
            post(() -> changeViewByStatus(status));
        }
    }

    /**
     * 根据status改变View视图
     * @param status 将要显示的View视图的status
     */
    private void changeViewByStatus(int status){
        if(mCurrentStatus == status) return;
        if(loadingView != null) if(status == STATUS_LOADING ) {loadingView.setVisibility(VISIBLE); loadingView.bringToFront();} else loadingView.setVisibility(GONE);
        if(errorView != null) if(status == STATUS_ERROR) {errorView.setVisibility(VISIBLE);loadingView.bringToFront();} else errorView.setVisibility(GONE);
        if(emptyView != null) if(status == STATUS_EMPTY) {emptyView.setVisibility(VISIBLE);emptyView.bringToFront();} else emptyView.setVisibility(GONE);
        if(status == STATUS_SUCCESS) {contentView.setVisibility(VISIBLE); contentView.bringToFront();} else contentView.setVisibility(GONE);
        mCurrentStatus = status;
    }


    /**
     * 把StatusView中的View全部移除
     * @param views View集合
     */
    private void clear(View... views) {
        if (null == views) {
            return;
        }
        try {
            for (View view : views) {
                if (null != view) {
                    removeView(view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder{

        private StatusView mStatusView;

        public Builder(Context context){
            mStatusView = new StatusView(context);
        }

        /**
         * 把loadingView添加到StatusView中，
         */
        public Builder setLoadingView(View loadingView){
            mStatusView.loadingView = loadingView;
            mStatusView.loadingView.setVisibility(GONE);
            mStatusView.addView(loadingView, DEFAULT_LP);
            return this;
        }

        /**
         * 把errorView添加到StatusView中，
         */
        public Builder setErrorView(View errorView){
            mStatusView.errorView = errorView;
            mStatusView.errorView.setVisibility(GONE);
            mStatusView.addView(errorView, DEFAULT_LP);
            return this;
        }

        public Builder setReloadClick(View.OnClickListener click){
            mStatusView.reloadClick = click;
            return this;
        }

        /**
         * 把emptyView添加到StatusView中，
         */
        public Builder setEmptyView(View emptyView){
            mStatusView.emptyView = emptyView;
            mStatusView.emptyView.setVisibility(GONE);
            mStatusView.addView(emptyView, DEFAULT_LP);
            return this;
        }

        /**
         * 把contentView添加到StatusView中，并且把StatusView添加到contentView的父布局
         */
        public Builder setContentView(View warppedView){
            mStatusView.wrappedView = warppedView;
            //Loading中已经做了包装，这里一定是ViewGroup
            ViewGroup warpped  = (ViewGroup)warppedView;
            //从warpped(warrped是Farmelayout, 里面包裹着Activity或Fragment或View)中第一个View，是加载成功后显示的View
            View contentView = warpped.getChildAt(0);
            ViewGroup.LayoutParams lp = contentView.getLayoutParams();
            //然后从warpped中移除contentView
            warpped.removeView(contentView);
            //把StatusView添加到warpped中去，替换掉contentView
            warpped.addView(mStatusView, lp);
            //然后把contentView设置给StatusView
            mStatusView.contentView = contentView;
            mStatusView.contentView.setVisibility(VISIBLE);
            mStatusView.addView(contentView, DEFAULT_LP);
            return this;
        }

        /**
         * 构造StatusView
         */
        public StatusView create(){
            return mStatusView;
        }

    }

}
