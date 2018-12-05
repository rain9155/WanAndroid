package com.example.hy.wanandroid.view.hierarchy;

import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.di.module.fragment.HierarchySecondFragmentModule;
import com.example.hy.wanandroid.model.network.entity.Article;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchySecondPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchySecondFragment extends BaseLoadFragment implements HierarchySecondContract.View {

    @BindView(R.id.rv_hierarchy)
    RecyclerView rvHierarchySecondList;
    @BindView(R.id.normal_view)
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
    private int mArticlePosition = -1;//点击的位置

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
            mPresenter.loadMoreArticles(0, mId);
            isLoadMore = false;
        });
        srlHierarchyList.setOnLoadMoreListener(refreshLayout -> {
            mPageNum++;
            mPresenter.loadMoreArticles(mPageNum, mId);
            isLoadMore = true;
        });
        mArticlesAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            mArticlePosition = position;
            Article article = mArticleList.get(position);
            ArticleActivity.startActicityForResultByFragment(_mActivity, this, article.getLink(), article.getTitle(), article.getId(), article.isCollect(), false, Constant.REQUEST_REFRESH_ARTICLE);
        });
        mArticlesAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            mArticlePosition = position;
            if(!User.getInstance().isLoginStatus()) {
                LoginActivity.startActivityForResultByFragment(_mActivity, this, Constant.REQUEST_COLLECT_ARTICLE);
                showToast(getString(R.string.first_login));
                return;
            }
            Article article = mArticleList.get(position);
            if(article.isCollect()) mPresenter.unCollectArticle(article.getId());
            else mPresenter.collectArticle(article.getId());
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.subscribleEvent();
        mPresenter.loadArticles(0, mId);
    }

    @Override
    public void showArticles(List<Article> articleList) {
        mArticleList.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreArticles(List<Article> articleList) {
        if(isLoadMore){
            srlHierarchyList.finishLoadMore();
        }else {
            if(!CommonUtil.isEmptyList(articleList)) mArticleList.clear();
            srlHierarchyList.finishRefresh();
        }
        mArticleList.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void topping() {
        if(rvHierarchySecondList != null) rvHierarchySecondList.smoothScrollToPosition(0);
    }

    @Override
    public void collectArticleSuccess() {
        showToast(getString(R.string.common_collection_success));
        mArticleList.get(mArticlePosition).setCollect(true);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.common_uncollection_success));
        mArticleList.get(mArticlePosition).setCollect(false);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
    }

    @Override
    public void refreshCollections(List<Integer> ids) {
        for(int i = 0; i < ids.size(); i++){
            for(int j = 0; j < mArticleList.size(); j++){
                if(mArticleList.get(j).getId() == ids.get(i)){
                    mArticleList.get(j).setCollect(false);
                    mArticlesAdapter.notifyItemChanged(j + mArticlesAdapter.getHeaderLayoutCount());
                    break;
                }
            }
        }
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadArticles(0, mId);
    }

    @Override
    public void unableRefresh() {
        if (isLoadMore) srlHierarchyList.finishLoadMore(); else srlHierarchyList.finishRefresh();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) return;
        Article article = mArticleList.get(mArticlePosition);
        switch (requestCode){
            case Constant.REQUEST_COLLECT_ARTICLE:
                if(article.isCollect()) mPresenter.unCollectArticle(article.getId());
                else mPresenter.collectArticle(article.getId());
                break;
            case Constant.REQUEST_REFRESH_ARTICLE:
                boolean isCollect = data.getBooleanExtra(Constant.KEY_DATA_RETURN, false);
                if(article.isCollect() != isCollect){
                    article.setCollect(isCollect);
                    mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getInt(Constant.KEY_HIERARCHY_PAGENUM, -1);
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

    public static Fragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_HIERARCHY_PAGENUM, id);
        Fragment fragment = new HierarchySecondFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
