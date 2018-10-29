package com.example.hy.wanandroid.view.project;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.project.ProjectContract;
import com.example.hy.wanandroid.di.component.activity.DaggerMainActivityComponent;
import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.network.entity.project.Project;
import com.example.hy.wanandroid.presenter.project.ProjectPresenter;
import com.example.hy.wanandroid.view.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

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

    @Inject
    ProjectPresenter mPresenter;
    @Inject
    List<String> mTitles;
    @Inject
    List<Integer> mIds;
    @Inject
    List<SupportFragment> mFragments;

    private VpAdapter mVpAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView() {
        if(!(getActivity() instanceof MainActivity)) return;
        ((MainActivity)getActivity()).getComponent().getProjectFragmentComponent(new ProjectFragmentModule()).inject(this);
        mPresenter.attachView(this);

        tlCommon.setTitle(R.string.menu_btm_nav_project);
        mVpAdapter = new VpAdapter(getChildFragmentManager(), mFragments, mTitles);
        vpProject.setAdapter(mVpAdapter);
        commonTablayout.setupWithViewPager(vpProject);
    }

    @Override
    protected void initData() {
        mPresenter.loadProjectList();
    }

    @Override
    public void showProjectList(List<Project> projectList) {

    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public static ProjectFragment newInstance() {
        return new ProjectFragment();
    }

}
