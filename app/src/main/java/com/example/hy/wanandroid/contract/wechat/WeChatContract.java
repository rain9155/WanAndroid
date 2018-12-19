package com.example.hy.wanandroid.contract.wechat;

import com.example.hy.wanandroid.base.presenter.IPresenter;
import com.example.hy.wanandroid.base.view.BaseView;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.model.network.entity.BaseResponse;
import com.example.hy.wanandroid.model.network.entity.Tab;

import java.util.List;

import io.reactivex.Observable;

/**
 * WeChat的Contract
 * Created by 陈健宇 at 2018/12/19
 */
public interface WeChatContract {

    interface View extends BaseView {
        void showWeChatTabs(List<Tab> tabs);
    }

    interface Presenter extends IPresenter<View> {
        void loadWeChatTabs();
    }

}
