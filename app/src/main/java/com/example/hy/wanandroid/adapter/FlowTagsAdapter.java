package com.example.hy.wanandroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.model.network.entity.search.HotKey;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

/**
 * 热词标签的适配器
 * Created by 陈健宇 at 2018/11/2
 */
public class FlowTagsAdapter extends TagAdapter<HotKey>{

    private TagFlowLayout mTagFlowLayout;

    public FlowTagsAdapter(List<HotKey> datas, TagFlowLayout tagFlowLayout) {
        super(datas);
        this.mTagFlowLayout = tagFlowLayout;
    }

    @Override
    public View getView(FlowLayout parent, int position, HotKey s) {
        if(s == null) return  null;
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.tv_flowlayout, mTagFlowLayout, false);
        textView.setText(s.getName());
        return textView;
    }
}
