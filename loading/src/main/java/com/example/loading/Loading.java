package com.example.loading;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.viewpager.widget.ViewPager;

/**
 * 一个控制视图切换逻辑的帮助类
 * Created by 陈健宇 at 2019/4/26
 */
public class Loading {

    private final SparseArray<View> mStatusViews = new SparseArray<>(3);
    private static volatile Loading sintance = null;
    private Loading(){}

    private static Loading getInstance(){
        if(sintance == null){
            Loading loading;
            synchronized (Loading.class){
                if(sintance == null){
                    loading = new Loading();
                    sintance = loading;
                }
            }
        }
        return sintance;
    }

    public static Builder beginBuildStatusView(Context context){
        return new Builder(context);
    }

    public static class Builder{

        private Loading mLoading;
        private View mWarppedView;
        private View mLoadingView;
        private View mErrorView;
        private View mEmptyView;
        private View.OnClickListener mReloadClick;
        private int mRetryId = -1;
        private Context mContext;
        private LayoutInflater mInflater;

        public Builder(Context context){
            mContext = context;
            mInflater = LayoutInflater.from(context);
            mLoading = Loading.getInstance();
        }

        public Builder addLoadingView(int id){
            mLoadingView = mInflater.inflate(id, null);
            return this;
        }

        public Builder addErrorView(int id){
            mErrorView = mInflater.inflate(id, null);
            return this;
        }

        public Builder addEmptyView(int id){
            mEmptyView = mInflater.inflate(id, null);
            return this;
        }

        private void removeParent(ViewParent parent, View removeView) {
            if(parent != null)
                ((ViewGroup)parent).removeView(removeView);
        }

        /**
         * 取出Activity中的包裹着contentView的View
         */
        public Builder warp(Activity activity){
            mWarppedView = activity.findViewById(android.R.id.content);
            return this;
        }

        /**
         * 新建一个ViewGroup包裹着传进来的View
         * @param view 要被包裹的View
         */
        public Builder warp(View view){
            //新建一个ViewGroup
            FrameLayout wrapper = new FrameLayout(view.getContext());
            ViewGroup.LayoutParams viewLp = view.getLayoutParams();
            if(viewLp != null) wrapper.setLayoutParams(viewLp);
            if(view.getParent() != null){
                ViewGroup parent = ((ViewGroup)view.getParent());
                int index = parent.indexOfChild(view);
                //从view的父布局移除View
                parent.removeView(view);
                //把ViewGroup添加到view的父布局中，并使用view原来的布局参数
                parent.addView(wrapper, index);
            }
            //把ViewGroup包裹着View
            FrameLayout.LayoutParams wrapperLp = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            wrapper.addView(view, 0, wrapperLp);
            mWarppedView = wrapper;
            return this;
        }

        /**
         * 重新加载任务
         * @param task 触发重新加载的逻辑的任务
         * @param retryId 因为触发重新加载一把是错误布局视图显示的时候，所以retryId代表errorView中触发重新加载的逻辑的控件的id
         */
        public Builder withReload(final Runnable task, int retryId){
            mReloadClick = v -> task.run();
            mRetryId = retryId;
            return this;
        }

        public void commit(){
            mLoading.mStatusViews.put(StatusView.STATUS_LOADING, mLoadingView);
            mLoading.mStatusViews.put(StatusView.STATUS_ERROR, mErrorView);
            mLoading.mStatusViews.put(StatusView.STATUS_EMPTY, mEmptyView);
        }

        /**
         * 创建一个StatusView，每个Activity或Fragment都应持有一个StatusView，用来控制视图切换逻辑
         * @return 创建好的StatusView实例
         */
        public StatusView create(){
            if(mWarppedView == null) throw new NullPointerException("must warp Activity or Fragment or View");
            StatusView.Builder builder = new StatusView.Builder(mContext);
            builder.setContentView(mWarppedView);
            if((mEmptyView = getView(mEmptyView, StatusView.STATUS_EMPTY)) != null){
                removeParent(mEmptyView.getParent(), mEmptyView);
                builder.setEmptyView(mEmptyView);
            }
            if((mErrorView = getView(mErrorView, StatusView.STATUS_ERROR)) != null){
                if(mReloadClick != null && mRetryId != -1)
                    mErrorView.findViewById(mRetryId).setOnClickListener(mReloadClick);
                removeParent(mErrorView.getParent(), mErrorView);
                builder.setErrorView(mErrorView);
            }
            if((mLoadingView = getView(mLoadingView, StatusView.STATUS_LOADING)) != null){
                removeParent(mLoadingView.getParent(), mLoadingView);
                builder.setLoadingView(mLoadingView);
            }
            return builder.create();
        }

        private View getView(View budilerView, int status){
            return  budilerView == null ? mLoading.mStatusViews.get(status) : budilerView;
        }
    }

}
