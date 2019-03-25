package com.example.hy.wanandroid.permission;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.bean.Permission;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

/**
 * 申请权限的代理Fragment
 * Created by 陈健宇 at 2019/3/25
 */
public class PermissionFragment extends Fragment {

    private Activity mActivity;
    private SparseArray<IPermissomCallback> mPermissomCallbacks = new SparseArray<>();
    private Random mRandom = new Random();

    public static PermissionFragment newInstance() {
        return new PermissionFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在re-created时Fragment不会重建
        setRetainInstance(true);
        mActivity = getActivity();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions, @NonNull IPermissomCallback callback){
        int requestCode = makeRequestCode();
        mPermissomCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void requestPermissions(@NonNull String[] permissions, int requestCode, @NonNull IPermissomCallback callback){
        mPermissomCallbacks.put(requestCode, callback);
        requestPermissions(permissions, requestCode);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       IPermissomCallback callback = mPermissomCallbacks.get(requestCode);
       if(callback == null) return;
       boolean allGranted = true;
       mPermissomCallbacks.remove(requestCode);
       for(int i = 0; i < grantResults.length; i++){
           int grantResult = grantResults[i];
           String name = permissions[i];
           Permission permission;
           if(grantResult == PackageManager.PERMISSION_GRANTED){
               permission = new Permission(name, true);
               callback.onAccepted(permission);
           }
           else{
               if(ActivityCompat.shouldShowRequestPermissionRationale(mActivity, name)){
                   permission = new Permission(name, false);
                   callback.onDenied(permission);
               }else{
                   permission = new Permission(name, false, false);
                   callback.onDeniedAndReject(permission);
               }
           }
       }
    }

    /**
     * 生成随机唯一的requestCode，最多尝试10次
     */
    private int makeRequestCode(){
        int requestCode;
        int tryCount = 0;
        do{
            requestCode = mRandom.nextInt(0x0000FFFF);
            tryCount++;
        }while (mPermissomCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }

    //回调
    public interface IPermissomCallback{
        void onAccepted(Permission permission);//授权
        void onDenied(Permission permission);//没有授权
        void onDeniedAndReject(Permission permission);//没有授权并勾选了don’t ask again
        void onAlreadyGranted();//已经同意了无需再授权权限 或 版本小于M
    }

}
