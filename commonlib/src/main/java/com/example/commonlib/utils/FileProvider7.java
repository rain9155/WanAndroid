package com.example.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import java.io.File;

import androidx.core.content.FileProvider;

/**
 * 适配Android 7.0的FileProvide，文件共享
 * Created by 陈健宇 at 2019/1/1
 */
public class FileProvider7 {

    /**
     * 适配获得url，7.0以上获得content://, 以下获得file://
     */
    public static Uri getUriForFile(Context context, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fileUri = getUriForFile24(context, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    /**
     * 通过file获得content://
     */
    public static Uri getUriForFile24(Context context, File file) {
        Uri fileUri = FileProvider.getUriForFile(
                context,
                context.getPackageName() + ".fileprovider",
                file);
        return fileUri;
    }


    /**
     * 适配apk安装的setDataAndType（）
     * @param intent 打开安装界面的intent
     * @param type  meta type
     * @param file  安装包的file
     * @param writeAble 是否写
     */
    public static void setIntentDataAndType(Context context, Intent intent, String type, File file, boolean writeAble) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            intent.setDataAndType(getUriForFile(context, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }
}
