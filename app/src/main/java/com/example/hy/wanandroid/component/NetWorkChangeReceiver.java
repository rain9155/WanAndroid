package com.example.hy.wanandroid.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.NetWorkChangeEvent;
import com.example.hy.wanandroid.model.DataModel;
import com.example.commonlib.utils.NetWorkUtil;

/**
 * Created by 陈健宇 at 2018/12/12
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {

    DataModel mDataModel;

    public NetWorkChangeReceiver() {
        super();
        mDataModel = App.getContext().getAppComponent().getDataModel();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isConnection = NetWorkUtil.isNetworkConnected(context);
        boolean hasNetWork = mDataModel.getNetWorkState();
        if(isConnection){
            if(isConnection != hasNetWork)//防止重复刷新
                RxBus.getInstance().post(new NetWorkChangeEvent(isConnection));
        }else {
            RxBus.getInstance().post(new NetWorkChangeEvent(isConnection));
        }
        mDataModel.setNetWorkState(isConnection);
    }

}
