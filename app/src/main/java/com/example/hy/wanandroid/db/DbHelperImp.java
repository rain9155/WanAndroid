package com.example.hy.wanandroid.db;

import android.database.Cursor;

import com.example.hy.wanandroid.db.bean.HistoryRecord;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.utils.LogUtil;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * 数据库
 * Created by 陈健宇 at 2018/11/2
 */
public class DbHelperImp implements DbHelper {

    @Inject
    public DbHelperImp() {
    }

    @Override
    public boolean addHistoryRecord(String record) {
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setRecord(record);
        return historyRecord.save();
    }

    @Override
    public int deleteOneHistoryRecord(String record) {
        return  LitePal.deleteAll(HistoryRecord.class, "record = ?", record);
    }

    @Override
    public int deleteAllHistoryRecord() {
       return LitePal.deleteAll(HistoryRecord.class);
    }

    @Override
    public boolean isExistHistoryRecord(String record) {
        return !CommonUtil.isEmptyList(LitePal.where("record = ?", record).find(HistoryRecord.class));
    }

    @Override
    public List<String> getAllHistoryRecord() {
        List<HistoryRecord> historyRecords = LitePal.findAll(HistoryRecord.class);
        if(CommonUtil.isEmptyList(historyRecords)) return null;
        List<String> histories = new ArrayList<>();
        for(HistoryRecord historyRecord : historyRecords) histories.add(historyRecord.getRecord());
        return histories;
    }
}
