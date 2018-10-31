package com.example.hy.wanandroid.view.hierarchy;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.FirstHierarchyAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.di.module.fragment.HierarchyFragmentModule;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchy;
import com.example.hy.wanandroid.network.entity.hierarchy.FirstHierarchyChild;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchyPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
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
    FirstHierarchyAdapter mListAdapter;
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
        ((MainActivity) getActivity()).getComponent().getHierarchyFragmentSubComponent(new HierarchyFragmentModule()).inject(this);
        mPresenter.attachView(this);

        tlCommon.setTitle(R.string.menu_btm_nav_hierarchy);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(_mActivity));
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
        mListAdapter.setOnItemClickListener(((adapter, view, position) -> starHierarchyActivity(position) ));

    }

    @Override
    protected void loadData() {
        mPresenter.loadFirstHierarchyList();
    }

    @Override
    public void showFirstHierarchyList(List<FirstHierarchy> firstHierarchyList) {
        if (CommonUtil.isEmptyList(firstHierarchyList)) mFirstHierarchyList.clear();
        mFirstHierarchyList.addAll(firstHierarchyList);
        mListAdapter.notifyDataSetChanged();
        if (isLoadMore) srlHierarchy.finishLoadMore(); else srlHierarchy.finishRefresh();
    }

    private void starHierarchyActivity(int position) {
        FirstHierarchy firstHierarchy = mFirstHierarchyList.get(position);
        if(firstHierarchy != null){
            ArrayList<String> listName = new ArrayList<>(firstHierarchy.getChildren().size());
            ArrayList<String> listId = new ArrayList<>(firstHierarchy.getChildren().size());
            for(FirstHierarchyChild child : firstHierarchy.getChildren()){
                listName.add(child.getName());
                listId.add(String.valueOf(child.getId()));
            }
            HierarchySecondActivity.startActivity(_mActivity, firstHierarchy.getName(), listId, listName);
        }
    }

    public static HierarchyFragment newInstance() {
        return new HierarchyFragment();
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
