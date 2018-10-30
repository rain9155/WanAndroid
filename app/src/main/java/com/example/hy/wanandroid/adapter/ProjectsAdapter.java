package com.example.hy.wanandroid.adapter;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.config.GlideApp;
import com.example.hy.wanandroid.network.entity.homepager.Article;

import java.util.List;

import androidx.annotation.Nullable;

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
        if(item != null){
            GlideApp.with(mContext)
                    .load(item.getEnvelopePic())
                    .placeholder(R.drawable.placeholder_android)
                    .into((ImageView) holder.getView(R.id.iv_image));

            holder.setText(R.id.tv_title, item.getTitle())
                    .setText(R.id.tv_details, item.getDesc())
                    .setText(R.id.tv_author, item.getAuthor())
                    .setText(R.id.tv_publish_time, item.getNiceDate());
        }
    }
}
