package com.example.hy.wanandroid.entity;

import java.util.List;

/**
 * 用户积分排名
 * Created by 陈健宇 at 2019/9/20
 */
public class CoinRank {

    /**
     * curPage : 1
     * datas:[]
     * offset : 0
     * over : false
     * pageCount : 139
     * size : 30
     * total : 4162
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<UserCoin> datas;

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<UserCoin> getDatas() {
        return datas;
    }

    public void setDatas(List<UserCoin> datas) {
        this.datas = datas;
    }
}
