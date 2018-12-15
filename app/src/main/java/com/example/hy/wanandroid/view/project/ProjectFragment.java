package com.example.hy.wanandroid.view.project;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.presenter.project.ProjectPresenter;
import com.example.hy.wanandroid.utils.StatusBarUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 项目tab
 * Created by 陈健宇 at 2018/10/23
 */
public class ProjectFragment extends BaseLoadFragment implements ProjectContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.normal_view)
    ViewPager vpProject;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;

    @Inject
    ProjectPresenter mPresenter;
    @Inject
    List<String> mTitles;
    @Inject
    List<Integer> mIds;
    @Inject
    List<Fragment> mFragments;

    private VpAdapter mVpAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getProjectFragmentComponent(new ProjectFragmentModule()).inject(this);
        mPresenter.attachView(this);

        StatusBarUtil.setHeightAndPadding(_mActivity, tlCommon);
        ivCommonSearch.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(R.string.homeFragment_project);
        tlCommon.setNavigationIcon(R.drawable.ic_navigation);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(_mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(_mActivity));
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadProjectList();
    }

    @Override
    public void showProjectList(List<Tab> projectList) {
        for (Tab project : projectList) {
            mIds.add(project.getId());
            mTitles.add(project.getName());
        }
        for (int i = 0; i < projectList.size(); i++) {
            mFragments.add(ProjectsFragment.newInstance(mIds.get(i)));
        }
        mVpAdapter = new VpAdapter(getChildFragmentManager(), mFragments, mTitles);
        vpProject.setAdapter(mVpAdapter);
        commonTablayout.setupWithViewPager(vpProject);
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadProjectList();
    }

    @Override
    public void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

}
