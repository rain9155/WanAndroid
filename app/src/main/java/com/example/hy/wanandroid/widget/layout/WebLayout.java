package com.example.hy.wanandroid.widget.layout;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.hy.wanandroid.R;
import com.just.agentweb.IWebLayout;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by 陈健宇 at 2018/11/8
 */
public class WebLayout implements IWebLayout {

    private Activity mActivity;
    private final TwinklingRefreshLayout mTwinklingRefreshLayout;
    private WebView mWebView = null;

    public WebLayout(Activity activity ) {
        this.mActivity = activity;
        mTwinklingRefreshLayout = (TwinklingRefreshLayout) LayoutInflater.from(activity).inflate(R.layout.web_layout, null, false);
        mTwinklingRefreshLayout.setPureScrollModeOn();
        mWebView = mTwinklingRefreshLayout.findViewById(R.id.webView);

    }

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return mTwinklingRefreshLayout;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return mWebView;
    }
}
