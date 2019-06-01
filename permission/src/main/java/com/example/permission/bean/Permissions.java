package com.example.permission.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 帮助构建多个权限实体类
 * Created by 陈健宇 at 2019/6/1
 */
public class Permissions {

    private List<Permission> mPermissions;

    public static Permissions wrap(String... permissions){
        return new Permissions(permissions);
    }

    private Permissions(){}

    private Permissions(String[] permissions){
        mPermissions = new ArrayList<>(permissions.length);
        for(String permisson : permissions){
            mPermissions.add(new Permission(permisson));
        }
    }

    /**
     * 获得Permission列表
=     */
    public List<Permission> getPermissions() {
        if (mPermissions == null) {
            return new ArrayList<>();
        }
        return mPermissions;
    }
}
