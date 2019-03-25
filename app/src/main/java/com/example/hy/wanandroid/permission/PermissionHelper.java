package com.example.hy.wanandroid.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import com.example.hy.wanandroid.bean.Permission;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 申请权限帮助类
 * Created by 陈健宇 at 2019/3/25
 */
public class PermissionHelper {

    private static final String TAG_PERMISSION_FRAGMENT = PermissionFragment.class.getName();
    private final Activity mActivity;
    private ArrayList<String> mPermissionsList;
    private static PermissionHelper sinstance = null;

    private PermissionHelper(Activity activity){
        mActivity = activity;
        mPermissionsList = new ArrayList<>();
    }

    public static PermissionHelper getInstance(Activity activity){
        if(sinstance == null)
            sinstance = new PermissionHelper(activity);
        return sinstance;
    }

    @SuppressLint("NewApi")
    public void requestPermissions(@NonNull String[] permissions, @NonNull PermissionFragment.IPermissomCallback callback){
        if(checkPermissions(permissions))
            callback.onAlreadyGranted();
        else
            getPermissionFragment(mActivity).requestPermissions(permissions, callback);
    }

    @SuppressLint("NewApi")
    public void requestPermissions(@NonNull String[] permissions, int requestCode, @NonNull PermissionFragment.IPermissomCallback callback){
        if(checkPermissions(permissions))
            callback.onAlreadyGranted();
        else
            getPermissionFragment(mActivity).requestPermissions(permissions, requestCode, callback);
    }

    /**
     * 检查是否符合申请条件
     */
    private boolean checkPermissions(@NonNull String[] permissions){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M) return true;
        boolean allGranted = true;
        mPermissionsList.clear();
        for(String permission : permissions){
            if(ContextCompat.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED){
                allGranted = false;
                mPermissionsList.add(permission);
            }
        }
        if(mPermissionsList.size() != 0)
            permissions = mPermissionsList.toArray(new String[mPermissionsList.size()]);
        return allGranted;
    }

    private PermissionFragment getPermissionFragment(Activity activity){
        if(!(activity instanceof FragmentActivity))
            throw new IllegalArgumentException("The argument passed must be FragmentActivity or it's sub class");

        FragmentManager manager = ((FragmentActivity)activity).getSupportFragmentManager();
        PermissionFragment fragment = (PermissionFragment) manager.findFragmentByTag(TAG_PERMISSION_FRAGMENT);

        if(fragment == null){
            fragment = PermissionFragment.newInstance();
            manager.beginTransaction()
                    .add(fragment, TAG_PERMISSION_FRAGMENT)
                    .commitNowAllowingStateLoss();
            manager.executePendingTransactions();
        }

        return fragment;
    }

}
