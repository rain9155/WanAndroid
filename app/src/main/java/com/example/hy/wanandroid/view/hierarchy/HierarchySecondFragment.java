package com.example.hy.wanandroid.view.hierarchy;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 第二级体系的Fragment
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchySecondFragment extends BaseFragment {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.vp_hierarchy_second)
    ViewPager vpHierarchySecond;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy_second;
    }

    @Override
    protected void initView() {
        
    }

    @Override
    protected void initData() {

    }
}
