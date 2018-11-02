package com.example.hy.wanandroid.db.bean;

import org.litepal.crud.LitePalSupport;

/**
 * 历史记录表实体类
 * Created by 陈健宇 at 2018/11/2
 */
public class HistoryRecord extends LitePalSupport{

    private String record;

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }
}
