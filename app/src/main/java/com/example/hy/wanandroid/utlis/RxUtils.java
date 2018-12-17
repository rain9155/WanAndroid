package com.example.hy.wanandroid.utlis;

import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.gson.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 对结果进行预处理
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleResult(){
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        if(tBaseResponse.getErrorCode() == 0){
                            return createObservable(tBaseResponse.getData());//创建我们需要的数据
                        }
                        return Observable.error(
                                new ApiException(tBaseResponse.getErrorCode(), tBaseResponse.getErrorMsg())//创建一个异常
                        );
                    }
                });
            }
        };
    }

    /**
     * 对结果进行预处理,只返回成功的结果
     */
    public static <T> ObservableTransformer<BaseResponse<T>, T> handleRequest2(){

        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                    @Override
                    public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                        return createObservable(tBaseResponse.getData());
                    }
                });
            }
        };
    }

    /**
     * 创建成功的数据源
     */
    private static <T> Observable<T> createObservable(T data){

        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                emitter.onNext(data);
                emitter.onComplete();
            }
        });
    }
}
