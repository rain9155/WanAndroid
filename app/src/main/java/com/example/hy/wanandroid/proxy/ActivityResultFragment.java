package com.example.hy.wanandroid.proxy;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseArray;

import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * 处理Activity回调结果的代理
 * Created by 陈健宇 at 2019/3/26
 */
public class ActivityResultFragment extends Fragment {

    private SparseArray<IResultCallback> mResultCallbacks = new SparseArray<>();
    private Random mRandom = new Random();

    public static ActivityResultFragment newInstance() {
        return new ActivityResultFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public void startActivityForResult(@NonNull Intent intent, @NonNull IResultCallback callback){
        int requestCode = makeRequestCode();
        mResultCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode, @NonNull IResultCallback callback){
        mResultCallbacks.put(requestCode, callback);
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IResultCallback callback = mResultCallbacks.get(requestCode);
        if(callback == null) return;

        mResultCallbacks.remove(requestCode);
        if(resultCode == Activity.RESULT_OK)
            callback.onResultOk(data);
        else if (requestCode == Activity.RESULT_CANCELED)
            callback.onResultCancel(data);
    }

    private int makeRequestCode(){
        int requestCode;
        int tryCount = 0;
        do{
            requestCode = mRandom.nextInt(0x0000FFFF);
            tryCount++;
        }while (mResultCallbacks.indexOfKey(requestCode) >= 0 && tryCount < 10);
        return requestCode;
    }

    //回调
    public interface IResultCallback{
        void onResultOk(Intent data);//setResult(OK)
        void onResultCancel(Intent data);//setResult(CANCEL)
    }

}
