package com.example.commonlib.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by 陈健宇 at 2019/1/10
 */
public class IntentUtil {

    /**
     * 获得打开Camera的Intent
     */
    public static Intent getCameraIntent(Context context){
        Uri imageUri = getCaptureImageUri(context);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        return intent;
    }

    /**
     * 获得所有可以Camera的Intent
     */
    public static List<Intent> getCameraIntents(Context context){
        List<Intent> allIntents = new ArrayList<>();
        Uri imageUri;
        //获得ImageUri
        imageUri = getCaptureImageUri(context);
        //遍历获得所有Camera的Intent
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCamera = context.getPackageManager().queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCamera) {
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (imageUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            }
            allIntents.add(intent);
        }
        return allIntents;
    }

    /**
     * 获得所有可以选择照片的应用的Intent
     */
    public static List<Intent> getGalleryIntents(Context context, String action, boolean includeDocuments) {
        List<Intent> intents = new ArrayList<>();
        Intent galleryIntent = new Intent(action);
        galleryIntent.setType("image/*");
        //遍历获得所有Gallery的Intent
        List<ResolveInfo> listGallery = context.getPackageManager().queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            intents.add(intent);
        }
        // remove documents intent
        if (!includeDocuments) {
            for (Intent intent : intents) {
                if (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity")) {
                    intents.remove(intent);
                    break;
                }
            }
        }
        return intents;
    }


    /**
     * 获得ImageUri
     */
    private static Uri getCaptureImageUri(Context context) {
        //创建File对象，用于存储拍照后的图片
        File outputImage = new File(context.getExternalCacheDir(), "pickImageResult.jpeg");
        try {
            if(outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //根据File获得ImageUri
        return FileProvider7.getUriForFile(context, outputImage);
    }


}
