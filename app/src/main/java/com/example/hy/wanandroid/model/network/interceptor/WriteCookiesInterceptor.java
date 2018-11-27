package com.example.hy.wanandroid.model.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.hy.wanandroid.utils.LogUtil;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 实现Interceptor器，用来将本地的cookie追加到http请求头中；
 * Created by 陈健宇 at 2018/11/18
 */
public class WriteCookiesInterceptor implements Interceptor {

    private SharedPreferences mPreferences;
    private static final String TAG = "cookie";

    public WriteCookiesInterceptor(Context context) {
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = (HashSet<String>) mPreferences.getStringSet(TAG, new HashSet<>());
        for(String cookie : cookies){
            builder.addHeader("Cookie", cookie);
            LogUtil.d(LogUtil.TAG_HTTP, "add Cookie: " + cookie);
        }
        return chain.proceed(builder.build());
    }
}
