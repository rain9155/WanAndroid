package com.example.hy.wanandroid.network.entity;

import java.util.List;

import io.reactivex.observers.ResourceObserver;

/**
 * 封装Observer
 * Created by 陈健宇 at 2018/10/26
 */
public class DefauleObserver extends ResourceObserver<BaseResponse>{

    @Override
    public void onNext(BaseResponse baseResponse) {
        if(baseResponse.getErrorCode() == 0){
            onSuccess(baseResponse.getData());
        }else {
            onFail(baseResponse.getErrorCode(), baseResponse.getErrorMsg());
        }
    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }

    /**
     * 非0数据都请求失败
     */
    public void onFail(int errorCode, String errorMesssage) {
        switch (errorCode){
            case -1001://登陆失效
                break;
            default://其他错误
                break;
        }
    }


    /**
     * 只有返回0数据才请求成功
     */
    public  <T> void onSuccess(T data) {
    }

}
