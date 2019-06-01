package com.example.permission.callback;

import com.example.permission.bean.Permission;

/**
 * 权限申请回调
 * Created by 陈健宇 at 2019/6/1
 */
public interface IPermissionCallback {
    void onAccepted(Permission permission);//授权了 或 已经同意了无需再授权权限 或 版本小于M
    void onDenied(Permission permission);//没有授权
    void onDeniedAndReject(Permission permission);//没有授权并勾选了don’t ask again
}
