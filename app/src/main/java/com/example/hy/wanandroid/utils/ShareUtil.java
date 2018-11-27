package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * 分享工具
 * Created by codeest on 2016/8/22.
 */
public class ShareUtil {

    /**
     * 分享文字
     */
    public static void shareText(Context context, String text, String title){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(intent, title));
    }

    /**
     * 发送邮件
     */
    public static void sendEmail(Context context, String address, String title) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                "mailto:" + address));
        context.startActivity(Intent.createChooser(intent, title));
    }
}
