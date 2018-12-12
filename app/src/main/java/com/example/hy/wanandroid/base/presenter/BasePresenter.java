package com.example.hy.wanandroid.base.presenter;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.NetWorkChangeEvent;
import com.example.hy.wanandroid.event.NightModeEvent;
import com.example.hy.wanandroid.event.StatusBarEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.entity.DefaultObserver;
import com.example.hy.wanandroid.utils.RxUtils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Presenter的基类
 * Created by 陈健宇 at 2018/10/21
 */
public class BasePresenter<T extends BaseView> implements IPresenter<T> {

    protected T mView;
    protected DataModel mModel;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    public BasePresenter(DataModel dataModel) {
        mModel = dataModel;
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
        addSubcriber(
                RxBus.getInstance().toObservable(NetWorkChangeEvent.class)
                .subscribe(netWorkChangeEvent -> mView.showTipsView(netWorkChangeEvent.isConnection()))
        );
    }

    @Override
    public boolean getNoImageState() {
        return mModel.getNoImageState();
    }

    @Override
    public boolean getAutoCacheState() {
        return mModel.getAutoCacheState();
    }

    @Override
    public boolean getNightModeState() {
        return mModel.getNightModeState();
    }

    @Override
    public boolean getStatusBarState() {
        return mModel.getStatusBarState();
    }
}
