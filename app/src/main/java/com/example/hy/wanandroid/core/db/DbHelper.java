package com.example.hy.wanandroid.core.db;

import java.util.List;

/**
 * 数据库操作接口
 * Created by 陈健宇 at 2018/11/2
 */
public interface DbHelper {

    /**
     * 添加历史记录
     */
    boolean addHistoryRecord(String record);

    /**
     * 删除某一条历史记录
     */
    int deleteOneHistoryRecord(String record);

    /**
     * 删除所有历史记录
     */
    int deleteAllHistoryRecord();

    /**
     * 查找某一条历史记录是否存在
     */
    boolean isExistHistoryRecord(String record);

    /**
     * 获得所有历史记录
     */
    List<String> getAllHistoryRecord();
}
