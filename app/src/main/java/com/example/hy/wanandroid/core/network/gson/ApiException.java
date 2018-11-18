package com.example.hy.wanandroid.core.network.gson;

/**
 * 自定义api异常
 * Created by 陈健宇 at 2018/10/27
 */
public class ApiException extends RuntimeException {

    private final int mErrorCode;
    private final String mErrorMessage;

    public ApiException(int errorCode, String errorMessage){
        this.mErrorCode = errorCode;
        this.mErrorMessage = errorMessage;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }
}
