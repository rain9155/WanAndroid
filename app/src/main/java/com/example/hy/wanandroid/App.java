package com.example.hy.wanandroid;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * Created by 陈健宇 at 2018/10/20
 */
public class App extends LitePalApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteDatabase db = LitePal.getDatabase();
    }
}
