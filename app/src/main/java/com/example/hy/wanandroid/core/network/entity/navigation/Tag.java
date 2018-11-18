package com.example.hy.wanandroid.core.network.entity.navigation;

import com.example.hy.wanandroid.core.network.entity.homepager.Article;

import java.util.List;

/**
 * 导航标签实体类
 * Created by 陈健宇 at 2018/10/31
 */
public class Tag {

    /**
     * articles : []
     * cid : 272
     * name : 常用网站
     */

    private int cid;
    private String name;
    private List<Article> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
