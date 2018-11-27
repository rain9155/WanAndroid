package com.example.hy.wanandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.model.network.entity.homepager.Article;
import com.example.hy.wanandroid.model.network.entity.navigation.Tag;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * 垂直标签每个子item的适配器
 * Created by 陈健宇 at 2018/11/1
 */
public class NavigationTagsAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {

    public NavigationTagsAdapter(int layoutResId, @Nullable List<Tag> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, final Tag item) {
        holder.setText(R.id.tv_tag, item.getName());
        final TagFlowLayout flowLayout = holder.getView(R.id.tfl_tag);
        flowLayout.setAdapter(new TagAdapter<Article>(item.getArticles()) {
            @Override
            public View getView(FlowLayout parent, int position, Article article) {
                if(article == null) return null;
                TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_flowlayout, flowLayout, false);
                textView.setText(article.getTitle());
                return textView;
            }
        });
        flowLayout.setOnTagClickListener((v, position, parent) -> {
            //跳转到详情页
            Article article = item.getArticles().get(position);
            ArticleActivity.startActivity(parent.getContext(), article.getLink(), article.getTitle(), article.getId(), article.isCollect(), false);
            return true;
        });
    }
}
