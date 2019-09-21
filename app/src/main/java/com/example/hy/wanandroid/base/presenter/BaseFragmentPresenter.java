package com.example.hy.wanandroid.base.presenter;

import androidx.annotation.CallSuper;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.event.ReLoadEvent;
import com.example.hy.wanandroid.model.DataModel;

import javax.inject.Inject;

/**
 * Fragment的Presenter基类
 * Created by 陈健宇 at 2019/9/20
 */
public class BaseFragmentPresenter<T extends IView> extends BasePresenter<T>{

    @Inject
    public BaseFragmentPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    @CallSuper
    public void subscribeEvent() {
        addSubscriber(RxBus.getInstance().toObservable(ReLoadEvent.class)
                .subscribe(reLoadEvent -> mView.reLoad()));
    }
}
