package com.example.hy.wanandroid.base.presenter;

import com.example.hy.wanandroid.base.view.IView;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Presenter的基类
 * Created by 陈健宇 at 2018/10/21
 */
public class BasePresenter<T extends IView> implements IPresenter<T> {

    protected T mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter() {
    }

    @Override
    public void attachView(T view) {
        this.mView = view;
    }

    @Override
    public boolean isAttachView() {
        return this.mView != null;
    }

    @Override
    public void detachView() {
        this.mView = null;
        if(mCompositeDisposable != null){
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void addSubcriber(Disposable disposable){
        if(mCompositeDisposable == null){
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    @Override
    public void subscribleEvent() {

    }
}
