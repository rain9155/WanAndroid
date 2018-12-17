package com.example.hy.wanandroid.model.network.interceptor;

import com.example.hy.wanandroid.config.App;
import com.example.commonlib.utils.NetWorkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Okhttp缓存拦截器
 * Created by 陈健宇 at 2018/12/5
 */
public class CacheInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isNetConnection = NetWorkUtil.isNetworkConnected(App.getContext());
        if(isNetConnection){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_NETWORK)//网络可用，强制从网络拉取数据
                    .build();
        }else {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)//网络不可用，从缓存获取
                    .build();
        }
        Response response = chain.proceed(request);
        if(isNetConnection){
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + 60 * 60)// 有网络时 设置缓存超时时间1个小时
                    .build();
        }else {
            response = response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 7 * 24 * 60 * 60)// 无网络时，设置超时为1周
                    .build();
        }
        return response;
    }

}
