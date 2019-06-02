package com.example.permission.callback;

import android.content.Intent;

import com.example.permission.bean.Permission;

/**
 * 特殊权限申请回调接口
 * Created by 陈健宇 at 2019/6/2
 */
public interface ISpecialPermissionCallback {

    void onAccepted(Permission permission);//setResult(OK)

    void onDenied(Permission permission);//setResult(CANCEL)

}
