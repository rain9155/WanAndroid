package com.example.permission;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.SparseArray;
import android.util.SparseIntArray;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.permission.bean.Permission;
import com.example.permission.bean.SpecialPermission;
import com.example.permission.callback.IPermissionsResultCallback;
import com.example.permission.utils.PermissionUtil;

import static android.app.Activity.RESULT_OK;

/**
 * 申请权限的代理Fragment
 * Created by 陈健宇 at 2019/3/25
 */
public class PermissionFragment extends Fragment {

    private Activity mActivity;
    private SparseArray<IPermissionsResultCallback> mPermissionsResult = new SparseArray<>();
    private SparseArray<SpecialPermission> mSpecialPermissions = new SparseArray<>();
    private Random mRandom = new Random();

    public static PermissionFragment newInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mActivity = getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions, @NonNull IPermissionsResultCallback callback){
        int requestCode = makeRequestCode();
        mPermissionsResult.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestSpecialPermission(SpecialPermission specialPermission, @NonNull IPermissionsResultCallback callback){
        int requestCode = makeRequestCode();
        mPermissionsResult.put(requestCode, callback);
        mSpecialPermissions.put(requestCode, specialPermission);
        requestSpecialPermission(specialPermission, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions, int requestCode, @NonNull IPermissionsResultCallback callback){
        mPermissionsResult.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       IPermissionsResultCallback callback = mPermissionsResult.get(requestCode);
       if(callback != null){
           mPermissionsResult.remove(requestCode);
           Permission[] permissionsResult = new Permission[permissions.length];
           for(int i = 0; i < grantResults.length; i++){
               int grantResult = grantResults[i];
               String name = permissions[i];
               Permission permission;
               if(grantResult == PackageManager.PERMISSION_GRANTED){
                   permission = new Permission(name, true);
               }else{
                   if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity, name)){
                       permission = new Permission(name, false);
                   }else{
                       permission = new Permission(name, false, false);
                   }
               }
               permissionsResult[i] = permission;
           }
           callback.OnPermissionsResult(permissionsResult);
       }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IPermissionsResultCallback callback = mPermissionsResult.get(requestCode);
        if(callback != null){
            mPermissionsResult.remove(requestCode);
                SpecialPermission special = mSpecialPermissions.get(requestCode);
                mSpecialPermissions.remove(requestCode);
                if(requestCode == RESULT_OK){
                boolean isGranted = false;
                Permission permission;
                switch (special){
                    case INSTALL_UNKNOWN_APP:
                        isGranted = PermissionUtil.checkSpecialInstallUnkownApp(mActivity);
                        break;
                    default:
                        break;
                }
                permission = new Permission(isGranted);
                callback.OnPermissionsResult(new Permission[]{permission});
            }
        }
    }

    private void requestSpecialPermission(SpecialPermission special, int requestCode) {
        switch (special){
            case INSTALL_UNKNOWN_APP:
                Uri selfPackageUri = Uri.parse("package:" + mActivity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, selfPackageUri);
                startActivityForResult(intent, requestCode);
                break;
            default:
                break;
        }

    }

    private int makeRequestCode(){
        int requestCode;
        int tryCount = 0;
        do{
            requestCode = mRandom.nextInt(0x0000FFFF);
            tryCount++;
        }while (mPermissionsResult.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }

}
