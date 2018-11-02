package com.example.hy.wanandroid.adapter;

import android.content.Context;

import com.example.hy.wanandroid.R;

import java.util.List;

import androidx.core.content.ContextCompat;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;

/**
 * 垂直标签名字的适配器
 * Created by 陈健宇 at 2018/11/1
 */
public class NavigationTagsNameAdapter implements TabAdapter {

    private List<String> mTagNames;
    private Context mContext;

    public NavigationTagsNameAdapter(Context context, List<String> tabNames) {
        mTagNames = tabNames;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mTagNames == null ? 0 : mTagNames.size();
    }

    @Override
    public ITabView.TabBadge getBadge(int position) {
        return null;
    }

    @Override
    public ITabView.TabIcon getIcon(int position) {
        return null;
    }

    @Override
    public ITabView.TabTitle getTitle(int position) {
        return new ITabView.TabTitle.Builder()
                .setContent(mTagNames.get(position))
                .setTextColor(ContextCompat.getColor(mContext, R.color.primaryText),
                        ContextCompat.getColor(mContext, R.color.secondaryText))
                .build();
    }

    @Override
    public int getBackground(int position) {
        return -1;
    }
}
