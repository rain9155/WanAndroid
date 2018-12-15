package com.example.hy.wanandroid.adapter;

import android.content.res.ColorStateList;
import android.text.Html;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.model.network.entity.Collection;
import com.example.hy.wanandroid.utils.AnimUtil;
import com.example.hy.wanandroid.utils.CommonUtil;

import java.util.List;
import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 收藏的Adapter
 * Created by 陈健宇 at 2018/11/22
 */
public class CollectionsAdapter extends BaseQuickAdapter<Collection, BaseViewHolder>{

    public CollectionsAdapter(int layoutResId, @Nullable List<Collection> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, Collection collection) {
        if(collection == null) return;
        holder.setText(R.id.tv_title, Html.fromHtml(collection.getTitle()))
                .setText(R.id.tv_author, "作者:" + collection.getAuthor())
                .setText(R.id.tv_classify, "分类:" + collection.getChapterName())
                .setText(R.id.tv_publish_time, collection.getNiceDate())
                .setImageDrawable(
                        R.id.iv_collection,
                        CommonUtil.getTintDrawable(
                                Objects.requireNonNull(ContextCompat.getDrawable(mContext, R.drawable.ic_home_collection)),
                                ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.colorCollected))
                        )
                )
                .addOnClickListener(R.id.iv_collection);
    }
}
