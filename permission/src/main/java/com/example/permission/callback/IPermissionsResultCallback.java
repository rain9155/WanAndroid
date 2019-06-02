package com.example.permission.callback;

import com.example.permission.bean.Permission;

/**
 * 统一权限回调处理接口
 * Created by 陈健宇 at 2019/6/2
 */
public interface IPermissionsResultCallback {

    /**
     * 内部使用，权限申请后的回调
     * @param permissionsResult 封装了权限信息的数组
     */
    void OnPermissionsResult(Permission[] permissionsResult);

}
