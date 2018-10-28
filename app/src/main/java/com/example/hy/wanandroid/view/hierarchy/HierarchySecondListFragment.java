package com.example.hy.wanandroid.view.hierarchy;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchySecondListFragment extends BaseFragment {

    @BindView(R.id.rv_hierarchy_second_list)
    RecyclerView rvHierarchySecondList;
    @BindView(R.id.srl_hierarchy_list)
    SmartRefreshLayout srlHierarchyList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy_second_list;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }
}
