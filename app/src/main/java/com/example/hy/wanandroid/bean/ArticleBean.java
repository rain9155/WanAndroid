package com.example.hy.wanandroid.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.hy.wanandroid.model.network.entity.Article;
import com.example.hy.wanandroid.model.network.entity.Collection;

import java.io.Serializable;

/**
 * Created by 陈健宇 at 2018/12/26
 */
public class ArticleBean implements Parcelable {

    private String mLink;//文章链接
    private String mTitle;//标题
    private int mId;//文章id
    private boolean isCollect;//文章是否被收藏

    public ArticleBean() { }

    public ArticleBean(Article article) {
        this.mLink = article.getLink();
        this.mTitle = article.getTitle();
        this.isCollect = article.isCollect();
        this.mId = article.getId();
    }

    public ArticleBean(Collection collection) {
        this.mLink = collection.getLink();
        this.mTitle = collection.getTitle();
        this.mId = collection.getId();
        this.isCollect = true;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public boolean isCollect() {
        return isCollect;
    }

    public void setCollect(boolean collect) {
        isCollect = collect;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLink);
        dest.writeString(mTitle);
        dest.writeInt(mId);
        dest.writeByte((byte) (isCollect ? 1 : 0));
    }

    public static final  Parcelable.Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
        @Override
        public ArticleBean createFromParcel(Parcel source) {
            ArticleBean articleBean = new ArticleBean();
            articleBean.mLink = source.readString();
            articleBean.mTitle = source.readString();
            articleBean.mId = source.readInt();
            articleBean.isCollect = source.readByte() != 0;
            return articleBean;
        }

        @Override
        public ArticleBean[] newArray(int size) {
            return new ArticleBean[size];
        }
    };
}
