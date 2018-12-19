package com.example.hy.wanandroid.presenter.wechat;

import com.example.hy.wanandroid.base.presenter.BasePresenter;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.contract.wechat.WeChatContract;
import com.example.hy.wanandroid.model.DataModel;
import com.example.hy.wanandroid.model.network.DefaultObserver;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.utlis.RxUtils;

import java.util.List;

import javax.inject.Inject;

/**
 * 项目Presenter
 * Created by 陈健宇 at 2018/10/23
 */
public class WeChatPresenter extends BasePresenter<WeChatContract.View> implements WeChatContract.Presenter{


    @Inject
    public WeChatPresenter(DataModel dataModel) {
        super(dataModel);
    }

    @Override
    public void loadWeChatTabs() {
        addSubcriber(
                mModel.getWeChatTabs()
                        .compose(RxUtils.switchSchedulers())
                        .compose(RxUtils.handleRequest2())
                        .subscribeWith(new DefaultObserver<List<Tab>>(mView ) {
                            @Override
                            public void onNext(List<Tab> tabs) {
                                super.onNext(tabs);
                                mView.showWeChatTabs(tabs);
                            }
                        }));
    }
}
