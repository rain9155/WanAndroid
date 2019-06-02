package com.example.permission.callback;

import com.example.permission.bean.Permission;

import java.util.List;

/**
 * 多个权限申请回调接口
 * Created by 陈健宇 at 2019/6/2
 */
public interface IPermissionsCallback {

    /**
     * 权限同意的回调，有三种情况：
     *（1）用户点击授权了一个或多个权限
     *（2）之前已经同意了无需再授权此权限
     *（3）系统版本小于M
     * @param permissions 用户同意授权的权限列表
     */
    void onAccepted(List<Permission> permissions);

    /**
     * 权限拒绝的回调，只有一种情况：
     * （1）用户点击拒绝授权一个或多个权限
     * @param permissions 用户拒绝授权的权限列表
     */
    void onDenied(List<Permission> permissions);

    /**
     * 权限拒绝的回调，你可以选择重写它并在里面处理逻辑，如引导用户到权限申请页同意一个或多个权限
     * 只有一种情况：
     * （1）没用户点击拒绝授权一个或多个权限，并勾选了don’t ask again
     * @param permissions 用户拒绝授权并勾选了don’t ask again的权限列表
     */
    default void onDeniedAndReject(List<Permission> permissions){}//没有授权并勾选了don’t ask again
}
