package com.example.hy.wanandroid.core.network.entity.hierarchy;

import com.example.hy.wanandroid.core.network.entity.homepager.Article;

import java.util.List;

/**
 * 第二级体系的实体类
 * Created by 陈健宇 at 2018/10/29
 */
public class SecondHierarchy {

    /**
     * curPage : 1
     * datas:[]
     * offset : 0
     * over : false
     * pageCount : 2
     * size : 20
     * total : 35
     */

    private int curPage;
    private int offset;
    private boolean over;
    private int pageCount;
    private int size;
    private int total;
    private List<Article> datas;

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

    public List<Article> getDatas() {
        return datas;
    }

    public void setDatas(List<Article> datas) {
        this.datas = datas;
    }
}

