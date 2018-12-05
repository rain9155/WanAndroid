package com.example.hy.wanandroid.view.hierarchy;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.FirstHierarchyAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.contract.hierarchy.HierarchyContract;
import com.example.hy.wanandroid.di.module.fragment.HierarchyFragmentModule;
import com.example.hy.wanandroid.model.network.entity.FirstHierarchy;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchyPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;
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
public class HierarchyFragment extends BaseLoadFragment implements HierarchyContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_hierarchy)
    RecyclerView rvHierarchy;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.normal_view)
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
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getHierarchyFragmentSubComponent(new HierarchyFragmentModule()).inject(this);
        mPresenter.attachView(this);

        ivCommonSearch.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(R.string.homeFragment_hierarchy);
        tlCommon.setNavigationIcon(R.drawable.ic_navigation);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(_mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(_mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(_mActivity));

        rvHierarchy.setLayoutManager(mLayoutManager);
        mListAdapter.openLoadAnimation();
        rvHierarchy.setAdapter(mListAdapter);
        rvHierarchy.setHasFixedSize(true);
        srlHierarchy.setOnLoadMoreListener(refreshLayout -> {
            mPresenter.loadMoreFirstHierarchyList();
            isLoadMore = true;
        });
        srlHierarchy.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadMoreFirstHierarchyList();
            isLoadMore = false;
        });
        mListAdapter.setOnItemClickListener(((adapter, view, position) -> starHierarchyActivity(position)));

    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.subscribleEvent();
        mPresenter.loadFirstHierarchyList();
    }

    @Override
    public void showFirstHierarchyList(List<FirstHierarchy> firstHierarchyList) {
        mFirstHierarchyList.addAll(firstHierarchyList);
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreFirstHierarchyList(List<FirstHierarchy> firstHierarchyList) {
        if (!CommonUtil.isEmptyList(firstHierarchyList)) mFirstHierarchyList.clear();
        mFirstHierarchyList.addAll(firstHierarchyList);
        mListAdapter.notifyDataSetChanged();
        if (isLoadMore) srlHierarchy.finishLoadMore();
        else srlHierarchy.finishRefresh();
    }

    @Override
    public void topping() {
        if(rvHierarchy != null) rvHierarchy.smoothScrollToPosition(0);
    }

    @Override
    public void unableRefresh() {
        if (isLoadMore) srlHierarchy.finishLoadMore(); else srlHierarchy.finishRefresh();
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadFirstHierarchyList();
    }

    /**
     * 启动HierarchyActivity
     * @param position 一级分类id
     */
    private void starHierarchyActivity(int position) {
        FirstHierarchy firstHierarchy = mFirstHierarchyList.get(position);
        if (firstHierarchy != null) {
            ArrayList<String> listName = new ArrayList<>(firstHierarchy.getChildren().size());
            ArrayList<String> listId = new ArrayList<>(firstHierarchy.getChildren().size());
            for (Tab child : firstHierarchy.getChildren()) {
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
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }
}
