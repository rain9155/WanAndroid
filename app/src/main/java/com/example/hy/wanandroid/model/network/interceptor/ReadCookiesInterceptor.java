package com.example.hy.wanandroid.model.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.hy.wanandroid.utils.LogUtil;
import java.io.IOException;
import java.util.HashSet;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 实现Interceptor器，将Http返回的cookie存储到本地
 * Created by 陈健宇 at 2018/11/17
 */
public class ReadCookiesInterceptor implements Interceptor {

    private SharedPreferences mPreferences;
    private static final String TAG = "cookie";

    public ReadCookiesInterceptor(Context context) {
        super();
        mPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        LogUtil.d(LogUtil.TAG_HTTP, "response: " + response.toString());
        if(!response.headers("Set-Cookie").isEmpty()){
            HashSet<String> cookies = new HashSet<>(response.headers("Set-Cookie"));
            mPreferences.edit().putStringSet(TAG, cookies).apply();
        }
        return response;
    }

}
