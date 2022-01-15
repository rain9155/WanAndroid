package com.example.hy.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import javax.inject.Inject;

/**
 * 历史记录的适配器
 * Created by 陈健宇 at 2018/11/2
 */
public class HistoryAdapter extends BaseQuickAdapter<String, BaseViewHolder>{

    @Inject
    public HistoryAdapter() {
        this(R.layout.item_search_history, new ArrayList<>());
    }

    public HistoryAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder holder, String item) {
        if(item != null)
            holder.setText(R.id.tv_history, item)
            .addOnClickListener(R.id.iv_clear);
    }

}
