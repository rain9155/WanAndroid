package com.example.hy.wanandroid.config;

import java.io.Serializable;

/**
 * 全局变量,需要序列化到本地
 * Created by 陈健宇 at 2018/10/25
 */
public class GlobalVariables implements Serializable, Cloneable{

    private static final long serialVersionUID = 1L;
    private static GlobalVariables instance;

    private GlobalVariables(){}

    public static GlobalVariables getInstance(){
        if(instance == null){
            synchronized (GlobalVariables.class){
                if(instance == null){
                    instance = new GlobalVariables();
                }
            }
        }
        return instance;
    }

}
