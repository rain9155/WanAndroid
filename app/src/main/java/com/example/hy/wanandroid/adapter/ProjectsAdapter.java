package com.example.hy.wanandroid.adapter;

import android.content.res.ColorStateList;
import android.text.Html;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.ImageUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import javax.inject.Inject;

/**
 * 项目列表的适配器
 * Created by 陈健宇 at 2018/10/30
 */
public class ProjectsAdapter extends BaseQuickAdapter<Article, BaseViewHolder>{

    @Inject
    public ProjectsAdapter() {
        this(R.layout.item_project_list, new ArrayList<>());
    }

    public ProjectsAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Article article) {
        if(article == null) return;

            holder.setText(R.id.tv_title, Html.fromHtml(article.getTitle()))
                    .setText(R.id.tv_details, article.getDesc())
                    .setText(R.id.tv_author, "作者:" + article.getAuthor())
                    .setText(R.id.tv_publish_time, article.getNiceDate())
                    .addOnClickListener(R.id.iv_collection);

        ImageUtil.loadImage(mContext, (ImageView)holder.getView(R.id.iv_image), article.getEnvelopePic());

        if(article.isCollect()){
            holder.setImageDrawable(
                    R.id.iv_collection,
                    CommonUtil.getTintDrawable(
                            Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.ic_home_collection)),
                            ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorCollected))
                    )
            );
        }
        else{
            holder.setImageResource(R.id.iv_collection, R.drawable.ic_home_collection);
        }
    }
}
