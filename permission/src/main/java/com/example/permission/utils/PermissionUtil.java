package com.example.permission.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.RequiresApi;

import com.example.permission.BuildConfig;
import com.example.permission.bean.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * 工具类
 * Created by 陈健宇 at 2019/6/2
 */
public class PermissionUtil {

    /**
     * 把Permission数组转为List集合
     * @param permissions Permission数组
     * @return PermissionList集合
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static List<Permission> toList(String[] permissions){
        List<Permission> allGrantedPermissions = new ArrayList<>(permissions.length);
        for (int i = 0; i < permissions.length; i++){
            String name = permissions[i];
            Permission permission = new Permission(name, true);
            allGrantedPermissions.add(permission);
        }
        return allGrantedPermissions;
    }

    /**
     * 把Permission集合转为数组
     * @param permissions Permission集合
     * @return Permission数组
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String[] toArray(List<Permission> permissions){
        String[] result = new String[permissions.size()];
        for (int i = 0; i < permissions.size(); i++){
            String name = permissions.get(i).name;
            result[i]= name;
        }
        return result;
    }

    /**
     * 检查特殊权限，安装未知来源应用
     * @return true表示用户同意授权，false表示用户拒绝授权
     */
    public static boolean checkSpecialInstallUnkownApp(Context context){
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return true;
        return context.getPackageManager().canRequestPackageInstalls();
    }

    /**
     * 跳转到不同厂商的Permission设置界面，不满足条件，默认跳到应用详情界面
     */
    public static void gotoPermissionDetail(Context context){
        String brand = Build.BRAND;
        if (TextUtils.equals(brand.toLowerCase(), "redmi") || TextUtils.equals(brand.toLowerCase(), "xiaomi")) {
            gotoMiuiPermission(context);
        } else if (TextUtils.equals(brand.toLowerCase(), "meizu")) {
            gotoMeizuPermission(context);
        } else if (TextUtils.equals(brand.toLowerCase(), "huawei") || TextUtils.equals(brand.toLowerCase(), "honor")) {
            gotoHuaweiPermission(context);
        } else {
           gotoAppDetail(context);
        }
    }

    /**
     * 跳转到应用详情界面
     */
    public static void gotoAppDetail(Context context) {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 华为的权限管理页面
     */
    public static void gotoHuaweiPermission(Context context) {
        try {
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");//华为权限管理
            intent.setComponent(comp);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            gotoAppDetail(context);
        }
    }

    /**
     * MIUI的权限管理页面
     */
    public static void gotoMiuiPermission(Context context) {
        try {
            // MIUI 8
            Intent intent = new Intent("miui.intent.doWork.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", context.getPackageName());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                // MIUI 5/6/7
                Intent intent = new Intent("miui.intent.doWork.APP_PERM_EDITOR");
                intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
                intent.putExtra("extra_pkgname", context.getPackageName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            } catch (Exception e1) {
                e1.printStackTrace();
                gotoAppDetail(context);
            }
        }
    }

    /**
      * 跳转到魅族的权限管理系统
      */
    public static void gotoMeizuPermission(Context context) {
          try {
              Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
              intent.addCategory(Intent.CATEGORY_DEFAULT);
              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
              intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
              context.startActivity(intent);
          } catch (Exception e) {
              e.printStackTrace();
              gotoAppDetail(context);
          }
    }


}
