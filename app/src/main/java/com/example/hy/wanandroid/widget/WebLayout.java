package com.example.hy.wanandroid.widget;

import android.app.Activity;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.just.agentweb.IWebLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by 陈健宇 at 2018/11/8
 */
public class WebLayout implements IWebLayout {

    private Activity mActivity;
    private WebView mWebView = null;

    @NonNull
    @Override
    public ViewGroup getLayout() {
        return null;
    }

    @Nullable
    @Override
    public WebView getWebView() {
        return null;
    }
}
