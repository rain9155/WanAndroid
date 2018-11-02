package com.example.hy.wanandroid.db;

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
        boolean is = historyRecord.save();
        LogUtil.i(LogUtil.TAG_COMMON, String.valueOf(is));
        return historyRecord.save();
    }

    @Override
    public void deleteOneHistoryRecord(String record) {
        LitePal.deleteAll(HistoryRecord.class, "record = ?", record);
    }

    @Override
    public void deleteAllHistoryRecord() {
        LitePal.deleteAll(HistoryRecord.class);
    }

    @Override
    public boolean isExistHistoryRecord(String record) {
        boolean is = CommonUtil.isEmptyList(LitePal.where("record = ?", record).find(HistoryRecord.class));
        LogUtil.i(LogUtil.TAG_COMMON, "isExist:" + String.valueOf(is));
        return is;
    }

    @Override
    public List<String> getAllHistoryRecord() {
        List<HistoryRecord> historyRecords = LitePal.findAll(HistoryRecord.class);
        LogUtil.i(LogUtil.TAG_COMMON, historyRecords.toString());
        if(CommonUtil.isEmptyList(historyRecords)) return null;
        List<String> histories = new ArrayList<>();
        for(HistoryRecord historyRecord : historyRecords) histories.add(historyRecord.getRecord());
        return histories;
    }
}
