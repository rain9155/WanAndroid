package com.example.hy.wanandroid.proxy;

import android.app.Activity;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/**
 * 处理Activity回调结果帮助类
 * Created by 陈健宇 at 2019/3/26
 */
public class ActivityResultHelper {

    private final static String TAG_RESULT_FRAGMENT = ActivityResultFragment.class.getName();
    private Activity mActivity;
    private static ActivityResultHelper sinstance = null;

    private ActivityResultHelper(Activity activity){
        mActivity = activity;
    }

    public static ActivityResultHelper getInstance(Activity activity){
        if(sinstance == null)
            sinstance = new ActivityResultHelper(activity);
        return sinstance;
    }

    public void startActivityForResult(@NonNull Intent intent, @NonNull ActivityResultFragment.IResultCallback callback){
        getActivityResultFragment(mActivity).startActivityForResult(intent, callback);
    }

    public void startActivityForResult(@NonNull Intent intent, int requestCode, @NonNull ActivityResultFragment.IResultCallback callback){
        getActivityResultFragment(mActivity).startActivityForResult(intent, requestCode, callback);
    }

    private ActivityResultFragment getActivityResultFragment(Activity activity){
        if(!(activity instanceof FragmentActivity))
            throw new IllegalArgumentException("The argument passed must be FragmentActivity or it's sub class");

        FragmentManager manager = ((FragmentActivity)activity).getSupportFragmentManager();
        ActivityResultFragment fragment = (ActivityResultFragment) manager.findFragmentByTag(TAG_RESULT_FRAGMENT);

        if(fragment == null){
            fragment = ActivityResultFragment.newInstance();
            manager.beginTransaction()
                    .add(fragment, TAG_RESULT_FRAGMENT)
                    .commitNowAllowingStateLoss();
            manager.executePendingTransactions();
        }

        return fragment;
    }

}
