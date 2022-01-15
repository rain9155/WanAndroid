package com.example.hy.wanandroid.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.entity.FirstHierarchy;
import com.example.hy.wanandroid.entity.Tab;
import com.example.hy.wanandroid.utlis.CommonUtil;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import javax.inject.Inject;

/**
 * 一级体系的adapter
 * Created by 陈健宇 at 2018/10/28
 */
public class FirstHierarchyAdapter extends BaseQuickAdapter<FirstHierarchy, BaseViewHolder>{

    @Inject
    public FirstHierarchyAdapter() {
        this(R.layout.item_hierarchy_first, new ArrayList<>());
    }

    public FirstHierarchyAdapter(int layoutResId, @Nullable List<FirstHierarchy> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FirstHierarchy item) {
        if(item == null) {
            return;
        }
        String secondLeverText = "";
        if(!CommonUtil.isEmptyList(item.getChildren())) {
            for (Tab child : item.getChildren()){
                secondLeverText += child.getName() + "  ";
            }
        }
        helper.setText(R.id.tv_first_lever, item.getName())
                .setText(R.id.tv_second_lever, secondLeverText);
    }
}
