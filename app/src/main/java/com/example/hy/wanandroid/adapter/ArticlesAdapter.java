package com.example.hy.wanandroid.adapter;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.Article;
import com.example.commonlib.utils.CommonUtil;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

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
        if(article == null) return;

        holder.setText(R.id.tv_title, Html.fromHtml(article.getTitle()))
                .setText(R.id.tv_author, "作者:" + article.getAuthor())
                .setText(R.id.tv_classify, "分类:" + article.getChapterName())
                .setText(R.id.tv_publish_time, article.getNiceDate())
                .addOnClickListener(R.id.iv_collection);

        if(article.isCollect()){
            holder.setImageDrawable(
                    R.id.iv_collection,
                    CommonUtil.getTintDrawable(
                            Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.ic_home_collection)),
                            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorCollected))
                    )
            );
        } else{
            holder.setImageResource(R.id.iv_collection, R.drawable.ic_home_collection);
        }

        TextView textView = holder.getView(R.id.tv_tags);
        textView.setVisibility(View.VISIBLE);
        if(article.isFresh()){
            Drawable freshDrawable = ContextCompat.getDrawable(mContext, R.drawable.bg_tag_tv1);
            ViewCompat.setBackground(textView, freshDrawable);
            textView.setText("最新");
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTag1));
        }else if(!CommonUtil.isEmptyList(article.getTags())){
            Drawable tagDrawable = ContextCompat.getDrawable(mContext, R.drawable.bg_tag_tv2);
            ViewCompat.setBackground(textView, tagDrawable);
            textView.setText(article.getTags().get(0).getName());
            textView.setTextColor(ContextCompat.getColor(mContext, R.color.colorTag2));
        }else {
            textView.setVisibility(View.INVISIBLE);
        }
    }
}
