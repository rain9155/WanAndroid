package com.example.hy.wanandroid.utils;

import android.content.Context;
import android.os.Environment;

import com.example.hy.wanandroid.config.App;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import okhttp3.Cookie;

/**
 * 一些公用方法工具类
 * Created by 陈健宇 at 2018/10/28
 */
public class CommonUtil {

    private CommonUtil() {
        throw new AssertionError();
    }

    /**
     * list判空
     */
    public static boolean isEmptyList(List<?> list){
        return list == null || list.size() == 0;
    }


    /**
     * 获得缓存路径
     */
    public static String getCachePath(Context context, String name){
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            cachePath = context.getExternalCacheDir().getPath();//获得外部存储空间的缓存路径
        } else {
            cachePath = context.getCacheDir().getPath();//获得内部存储空间的缓存路径
        }
        return cachePath + File.separator + name;
    }

    /**
     * 反序列化对象到内存
     */
    public static Object restoreObject(String fileName) {
        FileInputStream fileInputStream = null;
        ObjectInputStream objectInputStream = null;
        Object object = null;
        try {
            fileInputStream = App.getContext().openFileInput(fileName);
            objectInputStream = new ObjectInputStream(fileInputStream);
            object = objectInputStream.readObject();
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
           close(fileInputStream);
           close(objectInputStream);
        }
        return object;
    }

    /**
     * 序列化对象到本地
     */
    public static  void saveObject(String fileName, Object saveObject) {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = App.getContext().openFileOutput(fileName, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(saveObject);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(fileOutputStream);
            close(objectOutputStream);
        }
    }

    /**
     * 关闭流对象
     */
    public static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
