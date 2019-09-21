package com.example.hy.wanandroid.base.presenter;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.model.DataModel;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * 实现了MVP的Presenter基类
 * Created by 陈健宇 at 2018/10/21
 */
public abstract class BasePresenter<T extends IView> {

    protected T mView;
    protected DataModel mModel;
    private CompositeDisposable mCompositeDisposable;
    public abstract void subscribeEvent();//订阅事件

    public BasePresenter(DataModel dataModel) {
        mModel = dataModel;
    }

    /**
     * 注入View
     */
    public void attachView(T view) {
        this.mView = view;
    }

    /**
     * 判断是否注入了View
     */
    public boolean isAttachView() {
        return this.mView != null;
    }

    /**
     * 解除View
     */
    public void detachView() {
        this.mView = null;
        if(mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    /**
     * 订阅事件，管理事件生命周期
     */
    public void addSubscriber(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}
