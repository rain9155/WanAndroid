package com.example.hy.wanandroid.contract.wechat;

import com.example.hy.wanandroid.base.view.IView;
import com.example.hy.wanandroid.entity.Tab;

import java.util.List;

/**
 * WeChat的Contract
 * Created by 陈健宇 at 2018/12/19
 */
public interface WeChatContract {

    interface View extends IView {
        void showWeChatTabs(List<Tab> tabs);
    }

    interface Presenter {
        void loadWeChatTabs();
    }

}
