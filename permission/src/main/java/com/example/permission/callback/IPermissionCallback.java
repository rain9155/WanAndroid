package com.example.permission.callback;

import com.example.permission.bean.Permission;

/**
 * 单个权限申请回调接口
 * Created by 陈健宇 at 2019/6/1
 */
public interface IPermissionCallback {

    /**
     * 权限同意后回调，有三种情况：
     * （1）用户点击授权了这个权限
     * （2）之前已经同意了无需再授权此权限
     * （3）系统版本小于M
     * @param permission 封装了信息的权限
     */
    void onAccepted(Permission permission);

    /**
     * 权限拒绝后回调，只有一种情况：
     * （1）用户点击拒绝授权了这个权限
     * @param permission 封装了信息的权限
     */
    void onDenied(Permission permission);

    /**
     * 权限拒绝后回调，你可以选择重写它并在里面处理逻辑，如引导用户到权限申请页同意这个权限
     * 只有一种情况：
     * （1）没用户点击拒绝授权了这个权限，并勾选了don’t ask again
     * @param permission 封装了信息的权限
     */
    default void onDeniedAndReject(Permission permission){}
}
