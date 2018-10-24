package com.example.hy.wanandroid.view.hierarchy;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 体系tab
 * Created by 陈健宇 at 2018/10/23
 */
public class HierarchyFragment extends BaseFragment<HierarchyContract.Presenter> implements HierarchyContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_hierarchy)
    RecyclerView rvHierarchy;
    @BindView(R.id.srl_hierarchy)
    SmartRefreshLayout srlHierarchy;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy;
    }

    @Override
    protected void initView() {
        tlCommon.setTitle(R.string.menu_btm_nav_hierarchy);
    }

    @Override
    protected void initData() {
    }

    public static HierarchyFragment newInstance() {
        return new HierarchyFragment();
    }

}
