package com.example.hy.wanandroid.view.project;

import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ProjectsAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.project.ProjectsContract;
import com.example.hy.wanandroid.di.module.fragment.ProjectFragmentModule;
import com.example.hy.wanandroid.model.network.entity.homepager.Article;
import com.example.hy.wanandroid.presenter.project.ProjectsPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.MainActivity;
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
 * 项目详情列表Fragment
 * Created by 陈健宇 at 2018/10/29
 */
public class ProjectsFragment extends BaseLoadFragment implements ProjectsContract.View {

    @BindView(R.id.rv_projects)
    RecyclerView rvProjectList;
    @BindView(R.id.normal_view)
    SmartRefreshLayout srlProjects;

    @Inject
    ProjectsPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ProjectsAdapter mProjectsAdapter;
    @Inject
    List<Article> mArticles;

    private int mPageNum = 0;
    private int mId;
    private boolean isLoadMore = false;
    private int mArticlePosition = -1;//点击的位置

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_projects;
    }

    @Override
    protected void initView() {
        if(!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getProjectFragmentComponent(new ProjectFragmentModule()).inject(this);
        mPresenter.attachView(this);

        //项目列表
        rvProjectList.setLayoutManager(mLinearLayoutManager);
        mProjectsAdapter.openLoadAnimation();
        rvProjectList.setAdapter(mProjectsAdapter);
        srlProjects.setOnLoadMoreListener(refreshLayout -> {
           mPageNum++;
           mPresenter.loadMoreProjects(mPageNum, mId);
           isLoadMore = true;
        });
        srlProjects.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadMoreProjects(0, mId);
            isLoadMore = false;
        });
        mProjectsAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            mArticlePosition = position;
            Article article = mArticles.get(position);
            ArticleActivity.startActicityForResultByFragment(_mActivity, this, article.getLink(), article.getTitle(), article.getId(), article.isCollect(), false, Constant.REQUEST_REFRESH_ARTICLE);
        });
        mProjectsAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            mArticlePosition = position;
            if(!User.getInstance().isLoginStatus()) LoginActivity.startActivityForResultByFragment(_mActivity, this, Constant.REQUEST_COLLECT_ARTICLE);
            Article article = mArticles.get(position);
            if(article.isCollect()) mPresenter.unCollectArticle(article.getId());
            else mPresenter.collectArticle(article.getId());
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.subscribleEvent();
        mPresenter.loadProjects(0, mId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) return;
        Article article = mArticles.get(mArticlePosition);
        switch (requestCode){
            case Constant.REQUEST_COLLECT_ARTICLE:
                if(article.isCollect()) mPresenter.unCollectArticle(article.getId());
                else mPresenter.collectArticle(article.getId());
                break;
            case Constant.REQUEST_REFRESH_ARTICLE:
                boolean isCollect = data.getBooleanExtra(Constant.KEY_DATA_RETURN, false);
                if(article.isCollect() != isCollect){
                    article.setCollect(isCollect);
                    mProjectsAdapter.notifyItemChanged(mArticlePosition + mProjectsAdapter.getHeaderLayoutCount());
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
            mId = bundle.getInt(Constant.KEY_PROJECT_ID, -1);
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

    @Override
    public void showProjects(List<Article> articleList) {
        mArticles.addAll(articleList);
        mProjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreProjects(List<Article> articleList) {
        if(isLoadMore){
            mArticles.addAll(articleList);
            srlProjects.finishLoadMore();
        }else {
            if(!CommonUtil.isEmptyList(mArticles)) mArticles.clear();
            mArticles.addAll(articleList);
            srlProjects.finishRefresh();
        }
        mProjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void topping() {
        if(rvProjectList != null) rvProjectList.smoothScrollToPosition(0);
    }

    @Override
    public void collectArticleSuccess() {
        showToast(getString(R.string.common_collection_success));
        mArticles.get(mArticlePosition).setCollect(true);
        mProjectsAdapter.notifyItemChanged(mArticlePosition + mProjectsAdapter.getHeaderLayoutCount());
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.common_uncollection_success));
        mArticles.get(mArticlePosition).setCollect(false);
        mProjectsAdapter.notifyItemChanged(mArticlePosition + mProjectsAdapter.getHeaderLayoutCount());
    }

    @Override
    public void refreshCollections(List<Integer> ids) {
        for(int i = 0; i < ids.size(); i++){
            for(int j = 0; j < mArticles.size(); j++){
                if(mArticles.get(j).getId() == ids.get(i)){
                    mArticles.get(j).setCollect(false);
                    mProjectsAdapter.notifyItemChanged(j + mProjectsAdapter.getHeaderLayoutCount());
                    break;
                }
            }
        }
    }


    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadProjects(0, mId);
    }

    @Override
    public void unableRefresh() {
        if(isLoadMore) srlProjects.finishLoadMore(); else srlProjects.finishRefresh();
    }

    public static Fragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_PROJECT_ID, id);
        Fragment fragment = new ProjectsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
