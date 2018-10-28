package com.example.hy.wanandroid.view.hierarchy;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.FirstHierarchyListAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.di.module.HierarchyFragmentModule;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchyPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 体系tab
 * Created by 陈健宇 at 2018/10/23
 */
public class HierarchyFragment extends BaseFragment implements HierarchyContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_hierarchy)
    RecyclerView rvHierarchy;
    @BindView(R.id.srl_hierarchy)
    SmartRefreshLayout srlHierarchy;

    @Inject
    HierarchyPresenter mPresenter;
    @Inject
    FirstHierarchyListAdapter mListAdapter;
    @Inject
    List<FirstHierarchy> mFirstHierarchyList;
    @Inject
    LinearLayoutManager mLayoutManager;

    private boolean isLoadMore;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy;
    }

    @Override
    protected void initView() {
        if(!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getMainActivityComponent().getHierarchyFragmentSubComponent(new HierarchyFragmentModule()).inject(this);
        mPresenter.attachView(this);

        tlCommon.setTitle(R.string.menu_btm_nav_hierarchy);
        rvHierarchy.setLayoutManager(mLayoutManager);
        mListAdapter.openLoadAnimation();
        rvHierarchy.setAdapter(mListAdapter);
        rvHierarchy.setHasFixedSize(true);
        srlHierarchy.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadFirstHierarchyList();
            isLoadMore = true;
        });
        srlHierarchy.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadFirstHierarchyList();
            isLoadMore = false;
        });
    }

    @Override
    protected void initData() {
        mPresenter.loadFirstHierarchyList();
    }

    public static HierarchyFragment newInstance() {
        return new HierarchyFragment();
    }

    @Override
    public void showFirstHierarchyList(List<FirstHierarchy> firstHierarchyList) {
        if (CommonUtil.isEmptyList(firstHierarchyList)) mFirstHierarchyList.clear();
        mFirstHierarchyList.addAll(firstHierarchyList);
        mListAdapter.notifyDataSetChanged();
        if (isLoadMore) srlHierarchy.finishLoadMore(); else srlHierarchy.finishRefresh();
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
