package com.example.hy.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.network.entity.homepager.Article;

import java.util.List;

import androidx.annotation.Nullable;

/**
 * 文章列表adapter
 * Created by 陈健宇 at 2018/10/27
 */
public class ArticlesAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public ArticlesAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Article article) {
        holder.setText(R.id.tv_title, article.getTitle())
                .setText(R.id.tv_author, "作者:" + article.getAuthor())
                .setText(R.id.tv_classify, "分类:" + article.getChapterName())
                .setText(R.id.tv_publish_time, article.getNiceDate());
    }
}
