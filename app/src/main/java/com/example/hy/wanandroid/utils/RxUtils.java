package com.example.hy.wanandroid.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;

/**
 * Rxjava简单封装
 * Created by 陈健宇 at 2018/11/4
 */
public class RxUtils {

    private RxUtils() {
        throw new AssertionError();
    }

    /**
     * 切换线程
     */
    public static <T> ObservableTransformer<T, T> switchSchedulers(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return null;
            }
        };
    }
}
