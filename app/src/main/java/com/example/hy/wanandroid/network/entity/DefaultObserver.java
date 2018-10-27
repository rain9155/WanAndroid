package com.example.hy.wanandroid.network.entity;

import android.net.ParseException;

import com.example.hy.wanandroid.R;
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

    @Override
    public void onError(Throwable e) {
        if(e instanceof ApiException){
            ApiException apiException = (ApiException)e;
            if(apiException.getErrorCode() == -1001){//token失效

            }else {//没有获取到数据
                LogUtil.e(LogUtil.TAG_NET, "nothing：" + e.getMessage());
            }
        }else if (e instanceof UnknownHostException){//网络不可用
            ToastUtil.showToast(App.getContext(), "unavailable");
            LogUtil.e(LogUtil.TAG_NET, "unavailable：" + e.getMessage());
        }else if(e instanceof InterruptedException){//网络超时
            ToastUtil.showToast(App.getContext(), "timeout");
            LogUtil.e(LogUtil.TAG_NET, "timeout：" + e.getMessage());
        }else if (e instanceof HttpException){//http错误
            LogUtil.e(LogUtil.TAG_NET, "http错误：" + e.getMessage());
        }else if(e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException){//解析错误
            LogUtil.e(LogUtil.TAG_NET, "解析错误：" + e.getMessage());
        } else {//未知连接错误
            ToastUtil.showToast(App.getContext(), "unknown");
            LogUtil.e(LogUtil.TAG_NET, "unknown：" + e.getMessage());
        }
    }

    @Override
    public void onComplete() {

    }
}
