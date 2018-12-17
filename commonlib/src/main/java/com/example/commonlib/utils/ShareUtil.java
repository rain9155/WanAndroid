package com.example.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.example.commonlib.R;

/**
 * 分享工具
 * Created by codeest on 2016/8/22.
 */
public class ShareUtil{

    public static void shareText(Context context, String text, String title) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.TEXT", text);
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(Intent.createChooser(intent, title));
        } else {
            ToastUtil.showToast(context, context.getString(R.string.share_unknown));
        }

    }

    public static void sendEmail(Context context, String address, String title) {
        Intent intent = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:" + address));
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            context.startActivity(Intent.createChooser(intent, title));
        } else {
            ToastUtil.showToast(context, context.getString(R.string.share_email_unknown));
        }

    }

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
