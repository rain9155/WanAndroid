package com.example.hy.wanandroid.utlis;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import com.example.hy.wanandroid.R;

/**
 * 分享工具
 * Created by codeest on 2016/8/22.
 */
public class ShareUtil{

    /**
     * 分享文本
     */
    public static void shareText(Context context, String text, String title) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            Intent shardIntent = Intent.createChooser(intent, title);
            context.startActivity(shardIntent);
        } else {
            ToastUtil.showToast(context, context.getString(R.string.share_unknown));
        }
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(Context context, String address, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) == null) {
           intent = new Intent(Intent.ACTION_SEND);
           intent.setType("text/plain");
           intent.putExtra(Intent.EXTRA_EMAIL, new String[]{address});
           intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null) {
            Intent emailIntent = Intent.createChooser(intent, title);
            context.startActivity(emailIntent);
        }else {
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if(context.getPackageManager().resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY) != null){
            context.startActivity(intent);
        }else {
            ToastUtil.showToast(context, context.getString(R.string.open_browser_unknown));
        }
    }

    /**
     * 跳转到应用详情界面
     */
    public static void gotoAppDetailIntent(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 复制字符串
     */
    public static void copyString(Context context, String text) {
        if(TextUtils.isEmpty(text)) {
            return;
        }
        ClipboardManager mClipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        assert mClipboardManager != null;
        mClipboardManager.setPrimaryClip(ClipData.newPlainText(null, text));
    }

}
