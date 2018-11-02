package com.example.hy.wanandroid.utils;

import java.util.List;

/**
 * 一些公用方法工具类
 * Created by 陈健宇 at 2018/10/28
 */
public class CommonUtil {

    private CommonUtil() {
        throw new AssertionError();
    }

    public static boolean isEmptyList(List<?> list){
        return list == null || list.size() == 0;
    }
}
