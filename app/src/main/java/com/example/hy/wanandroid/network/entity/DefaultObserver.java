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

import static com.example.hy.wanandroid.utils.LogUtil.TAG_ERROR;

/**
 * 封装Observer
 * Created by 陈健宇 at 2018/10/26
 */
public abstract class DefaultObserver<T> extends ResourceObserver<T>{

    private IView mView;
    private boolean isShowErrorView = true;
    private boolean isShowProgress = true;

    private DefaultObserver() {}

    protected DefaultObserver(IView view) {
        this(view, true, true);
    }

    protected DefaultObserver(IView view, boolean isShowErrorView) {
       this(view, isShowErrorView, true);
    }

    protected DefaultObserver(IView view, boolean isShowErrorView, boolean isShowProgress) {
        mView = view;
        this.isShowErrorView = isShowErrorView;
        this.isShowProgress = isShowProgress;
    }

    @Override
    protected void onStart() {
        if(isShowProgress) mView.showLoading();
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
                LogUtil.e(TAG_ERROR, "nothing：" + e.getMessage());
                emptyError();
            }
        }else{
            if(e instanceof UnknownHostException){
                LogUtil.e(TAG_ERROR, "unavailable：" + e.getMessage());
                unavaiableError();
            }else if(e instanceof InterruptedException){
                LogUtil.e(TAG_ERROR, "timeout：" + e.getMessage());
                timeoutError();
            }else if (e instanceof HttpException){
                LogUtil.e(TAG_ERROR, "http错误：" + e.getMessage());
                httpError();
            }else if(e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException){
                LogUtil.e(TAG_ERROR, "解析错误：" + e.getMessage());
                praseError();
            } else {
                LogUtil.e(TAG_ERROR, "unknown：" + e.getMessage());
                unknown();
            }
        }
    }

    /**
     * 未知错误
     */
    protected void unknown() {
        mView.showToast("未知连接错误");
        mView.unableRefresh();
        if(isShowErrorView) mView.showErrorView();
    }

    /**
     * 解析错误
     */
    protected void praseError(){
        mView.unableRefresh();
        if (isShowErrorView) mView.showErrorView();
    }

    /**
     * http错误
     */
    protected void httpError(){
        mView.unableRefresh();
        if (isShowErrorView) mView.showErrorView();
    }

    /**
     * 网络超时异常
     */
    protected void timeoutError() {
        mView.showToast("网络超时");
        mView.unableRefresh();
        if (isShowErrorView) mView.showErrorView();
    }

    /**
     * 网络不可用异常
     */
    protected void unavaiableError() {
        mView.showToast("网络不可用");
        mView.unableRefresh();
        if (isShowErrorView) mView.showErrorView();
    }

    /**
     * 空数据异常
     */
    protected void emptyError() {
        mView.showToast("获取不到数据");
        mView.unableRefresh();
        if (isShowErrorView) mView.showErrorView();
    }

    @Override
    public void onComplete() {

    }
}
