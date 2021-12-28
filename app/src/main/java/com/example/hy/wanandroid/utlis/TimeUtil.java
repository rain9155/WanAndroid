package com.example.hy.wanandroid.utlis;

import android.annotation.SuppressLint;
import android.util.SparseLongArray;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * 时间转换工具类
 * Created by 陈健宇 at 2019/9/21
 */
public class TimeUtil {

    private static SparseLongArray mTimeManager = new SparseLongArray();

    /**
     * 判断isInInterval两次调用之间的时间间隔是否在interval时间内
     */
    public static boolean isInInterval(int interval){
        if(mTimeManager.get(interval, -1) != -1){
            long preTime = mTimeManager.get(interval);
            if(System.currentTimeMillis() - preTime < interval){
                mTimeManager.delete(interval);
                return true;
            }
        }
        mTimeManager.put(interval, System.currentTimeMillis());
        return false;
    }

    /**
     * 判断isInInterval两次调用之间的时间是否间隔interval时间
     */
    public static boolean isOutInterval(int interval){
        if(mTimeManager.get(interval, -1) != -1){
            long preTime = mTimeManager.get(interval);
            if(System.currentTimeMillis() - preTime > interval){
                mTimeManager.delete(interval);
                return true;
            }
            return false;
        }else {
            mTimeManager.put(interval, System.currentTimeMillis());
            return true;
        }
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(Long stamp){
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(stamp);
        String res = simpleDateFormat.format(date);
        return res;
    }

}
