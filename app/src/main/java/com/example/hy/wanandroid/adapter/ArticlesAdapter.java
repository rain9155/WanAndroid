package com.example.hy.wanandroid.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.core.network.entity.homepager.Article;
import com.example.hy.wanandroid.utils.CommonUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 文章列表adapter
 * Created by 陈健宇 at 2018/10/27
 */
public class ArticlesAdapter extends BaseQuickAdapter<Article, BaseViewHolder> {

    public ArticlesAdapter(int layoutResId, @Nullable List<Article> data, Context context) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Article article) {
        if(article == null) return;

        holder.setText(R.id.tv_title, Html.fromHtml(article.getTitle()))
                .setText(R.id.tv_author, "作者:" + article.getAuthor())
                .setText(R.id.tv_classify, "分类:" + article.getChapterName())
                .setText(R.id.tv_publish_time, article.getNiceDate())
                .addOnClickListener(R.id.iv_collection);

        if(article.isCollect()) holder.setImageDrawable(R.id.iv_collection, CommonUtil.getTintDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_home_collection), ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorCollected))));
        else holder.setImageResource(R.id.iv_collection, R.drawable.ic_home_collection);
    }
}
