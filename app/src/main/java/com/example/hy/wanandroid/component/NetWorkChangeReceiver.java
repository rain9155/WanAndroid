package com.example.hy.wanandroid.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.hy.wanandroid.config.RxBus;
import com.example.hy.wanandroid.event.NetWorkChangeEvent;
import com.example.utilslibrary.NetWorkUtil;

/**
 * Created by 陈健宇 at 2018/12/12
 */
public class NetWorkChangeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        RxBus.getInstance().post(new NetWorkChangeEvent(NetWorkUtil.isNetworkConnected(context)));
    }

}
