package com.example.hy.wanandroid.view.hierarchy;

import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.di.module.fragment.HierarchySecondFragmentModule;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchySecondPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment;

/**
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchySecondFragment extends BaseFragment implements HierarchySecondContract.View {

    @BindView(R.id.rv_hierarchy_second_list)
    RecyclerView rvHierarchySecondList;
    @BindView(R.id.srl_hierarchy_list)
    SmartRefreshLayout srlHierarchyList;

    @Inject
    HierarchySecondPresenter mPresenter;
    @Inject
    ArticlesAdapter mArticlesAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    List<Article> mArticleList;

    private int mPageNum = 0;
    private int mId = -1;
    private boolean isLoadMore = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy_second;
    }

    @Override
    protected void initView() {
        if(!(getActivity() instanceof HierarchySecondActivity)) return;
        ((HierarchySecondActivity) getActivity()).getComponent().getHierarchySecondFragmentComponent(new HierarchySecondFragmentModule()).inject(this);
        mPresenter.attachView(this);

        mArticlesAdapter.openLoadAnimation();
        rvHierarchySecondList.setHasFixedSize(true);
        rvHierarchySecondList.setLayoutManager(mLinearLayoutManager);
        rvHierarchySecondList.setAdapter(mArticlesAdapter);
        srlHierarchyList.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadArticles(0, mId);
            isLoadMore = false;
        });
        srlHierarchyList.setOnLoadMoreListener(refreshLayout -> {
            mPageNum++;
            mPresenter.loadArticles(mPageNum, mId);
            isLoadMore = true;
        });
    }

    @Override
    protected void initData() {
        mPresenter.loadArticles(0, mId);
    }

    @Override
    public void showArticles(List<Article> articleList) {
        if(isLoadMore){
            mArticleList.addAll(articleList);
            srlHierarchyList.finishLoadMore();
        }else {
            if(CommonUtil.isEmptyList(articleList)) mArticleList.clear();
            mArticleList.addAll(articleList);
            srlHierarchyList.finishRefresh();
        }
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getInt(Constant.KEY_PAGENUM, -1);
        }
    }

    @Override
    public void onDestroy() {
        if(mPresenter != null){
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public static SupportFragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_PAGENUM, id);
        SupportFragment fragment = new HierarchySecondFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
