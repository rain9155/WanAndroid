package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.example.hy.wanandroid.R;
import com.example.utilslibrary.ToastUtil;

/**
 * 分享工具
 * Created by codeest on 2016/8/22.
 */
public class ShareUtil extends com.example.utilslibrary.ShareUtil {

    /**
     * 打开浏览器
     */
    public static void openBrowser(Context context, String address){
        if (TextUtils.isEmpty(address) || address.startsWith("file://")) {
            ToastUtil.showToast(context, context.getString(R.string.articleActivity_browser_error));
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(address));
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(intent);
        }else {
            ToastUtil.showToast(context, context.getString(R.string.open_browser_unknown));
        }
    }

}
