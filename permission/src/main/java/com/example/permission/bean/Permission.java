package com.example.permission.bean;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 权限实体类
 * Created by 陈健宇 at 2019/3/24
 */
public class Permission {

    public final String name;
    public final boolean granted;
    public final boolean shouldShowRequestPermissionRationable;
    public final SpecialPermission specialPermission;


    public Permission(String name){
        this(name, false);
    }

    public Permission(String name, boolean granted){
        this(name, granted, true, null);
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationable){
        this(name, granted, shouldShowRequestPermissionRationable, null);
    }

    public Permission(boolean granted, SpecialPermission specialPermission){
        this(specialPermission.name(), granted, true, specialPermission);
    }

    public Permission(String name, boolean granted, boolean shouldShowRequestPermissionRationable, SpecialPermission specialPermission){
        this.name = name;
        this.granted = granted;
        this.shouldShowRequestPermissionRationable = shouldShowRequestPermissionRationable;
        this.specialPermission = specialPermission;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        final Permission permission = (Permission)obj;
        if(this.granted != permission.granted) return false;
        if(this.shouldShowRequestPermissionRationable != permission.shouldShowRequestPermissionRationable) return false;
        if(this.specialPermission != permission.specialPermission) return false;
        return this.name.equals(permission.name);
    }

    @Override
    public int hashCode() {
        int hash = name.hashCode();
        hash = 31 * hash + (granted ? 1 : 0);
        hash = 31 * hash + (shouldShowRequestPermissionRationable ? 1 : 0);
        return hash;
    }

    @NonNull
    @Override
    public String toString() {
        return "[PermissonName = " + name
                + ", Granted = " + granted
                + ", ShouldShowRequestPermissionRationable = " + shouldShowRequestPermissionRationable
                + ", SpecialPermission = " + specialPermission.name() + "]";
    }
}
