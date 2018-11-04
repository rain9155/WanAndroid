package com.example.hy.wanandroid.network.entity;

import android.net.ParseException;
import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.network.gson.ApiException;
import com.example.hy.wanandroid.utils.LogUtil;
import com.example.hy.wanandroid.utils.ToastUtil;
import com.google.gson.JsonParseException;

import org.json.JSONException;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.List;

import io.reactivex.observers.ResourceObserver;
import retrofit2.HttpException;

/**
 * 封装Observer
 * Created by 陈健宇 at 2018/10/26
 */
public abstract class DefaultObserver<T> extends ResourceObserver<T>{

    private IView mView;
    private boolean isShowErrorView = true;

    protected DefaultObserver() {
    }

    protected DefaultObserver(IView view) {
        mView = view;
    }

    protected DefaultObserver(IView view, boolean isShowErrorView) {
        mView = view;
        this.isShowErrorView = isShowErrorView;
    }

    @Override
    public void onNext(T t) {
        mView.showNormalView();
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            ApiException apiException = (ApiException)e;
            if(apiException.getErrorCode() == -1001){//token失效
                //TODO
            }else {//没有获取到数据

                LogUtil.e(LogUtil.TAG_ERROR, "nothing：" + e.getMessage());
                mView.showToast("获取到空数据");

                if (isShowErrorView) mView.showErrorView();
            }
        }else{
            if(e instanceof UnknownHostException){//网络不可用

                LogUtil.e(LogUtil.TAG_ERROR, "unavailable：" + e.getMessage());
                mView.showToast("网络不可用");

            }else if(e instanceof InterruptedException){//网络超时

                LogUtil.e(LogUtil.TAG_ERROR, "timeout：" + e.getMessage());
                mView.showToast("网络超时");

            }else if (e instanceof HttpException){//http错误

                LogUtil.e(LogUtil.TAG_ERROR, "http错误：" + e.getMessage());

            }else if(e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){//解析错误

                LogUtil.e(LogUtil.TAG_ERROR, "解析错误：" + e.getMessage());

            } else {//未知连接错误

                LogUtil.e(LogUtil.TAG_ERROR, "unknown：" + e.getMessage());
                mView.showToast("未知连接错误");
            }

            if(isShowErrorView) mView.showErrorView();

        }
    }

    @Override
    public void onComplete() {

    }
}
