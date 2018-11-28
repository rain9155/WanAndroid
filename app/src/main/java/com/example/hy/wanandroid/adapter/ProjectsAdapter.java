package com.example.hy.wanandroid.adapter;

import android.content.res.ColorStateList;
import android.text.Html;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.App;
import com.example.hy.wanandroid.config.GlideApp;
import com.example.hy.wanandroid.model.network.entity.homepager.Article;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.utils.ImageUtil;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 项目列表的适配器
 * Created by 陈健宇 at 2018/10/30
 */
public class ProjectsAdapter extends BaseQuickAdapter<Article, BaseViewHolder>{


    public ProjectsAdapter(int layoutResId, @Nullable List<Article> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Article item) {
        if(item == null) return;

            holder.setText(R.id.tv_title, Html.fromHtml(item.getTitle()))
                    .setText(R.id.tv_details, item.getDesc())
                    .setText(R.id.tv_author, "作者:" + item.getAuthor())
                    .setText(R.id.tv_publish_time, item.getNiceDate())
                    .addOnClickListener(R.id.iv_collection);

        ImageUtil.loadImage(mContext, (ImageView)holder.getView(R.id.iv_image), item.getEnvelopePic());

        if(item.isCollect()) holder.setImageDrawable(R.id.iv_collection, CommonUtil.getTintDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_home_collection), ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorCollected))));
        else holder.setImageResource(R.id.iv_collection, R.drawable.ic_home_collection);

    }
}
