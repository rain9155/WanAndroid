package com.example.hy.wanandroid.view.project;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 项目tab
 * Created by 陈健宇 at 2018/10/23
 */
public class ProjectFragment extends BaseFragment implements ProjectContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.vp_project)
    ViewPager vpProject;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {
        tlCommon.setTitle(R.string.menu_btm_nav_project);
    }

    @Override
    protected void initData() {
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

}
