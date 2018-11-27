package com.example.hy.wanandroid.event;

import java.util.List;

/**
 * 收藏事件
 * Created by 陈健宇 at 2018/11/25
 */
public class CollectionEvent {

    private List<Integer> mIds;

    public CollectionEvent(List<Integer> ids) {
        mIds = ids;
    }

    public List<Integer> getIds() {
        return mIds;
    }
}
