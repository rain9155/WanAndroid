package com.example.hy.wanandroid.view.project;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ProjectsAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.entity.ArticleBean;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.project.ProjectsContract;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.presenter.project.ProjectsPresenter;
import com.example.hy.wanandroid.utlis.AnimUtil;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.example.hy.wanandroid.widget.popup.PressPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dagger.Lazy;

import static android.app.Activity.RESULT_OK;

/**
 * 项目详情列表Fragment
 * Created by 陈健宇 at 2018/10/29
 */
public class ProjectsFragment extends BaseLoadFragment<ProjectsPresenter> implements ProjectsContract.View {

    private static final String TAG = ProjectsFragment.class.getSimpleName();

    @BindView(R.id.rv_projects)
    RecyclerView rvProjectList;
    @BindView(R.id.srl_projects)
    SmartRefreshLayout srlProjects;

    @Inject
    ProjectsPresenter mPresenter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ProjectsAdapter mProjectsAdapter;
    @Inject
    Lazy<PressPopup> mPopupWindow;

    private int mPageNum = 1;
    private int mId;
    private boolean isLoadMore = false;
    private int mArticlePosition = 0;//点击的位置
    private Article mArticle;
    private boolean isPress = false;
    private List<Article> mArticles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_projects;
    }

    @Override
    protected ProjectsPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected void initView() {
        super.initView();
        initRecyclerView();
        initRefreshView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView() {
        mArticles = mProjectsAdapter.getData();
        //项目列表
        rvProjectList.setLayoutManager(mLinearLayoutManager);
        mProjectsAdapter.openLoadAnimation();
        rvProjectList.setAdapter(mProjectsAdapter);
        mProjectsAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            if(CommonUtil.isEmptyList(mArticles)) return;
            mArticlePosition = position;
            mArticle = mArticles.get(position);
            ArticleBean articleBean = new ArticleBean(mArticle);
            articleBean.setLink(getHttps(articleBean.getLink()));
            ArticleActivity.startActicityForResultByFragment(mActivity, this, articleBean, false, Constant.REQUEST_REFRESH_ARTICLE);
        });
        mProjectsAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            if(CommonUtil.isEmptyList(mArticles)) return;
            mArticlePosition = position;
            mArticle =  mArticles.get(position);
            if(!User.getInstance().isLoginStatus()){
                LoginActivity.startActivityForResultByFragment(mActivity, this, Constant.REQUEST_LOGIN);
                showToast(getString(R.string.first_login));
                return;
            }
            collect();
            AnimUtil.scale(view, -1);
        });
        mProjectsAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if(CommonUtil.isEmptyList(mArticles)) return false;
            Article article = mArticles.get(position);
            mPopupWindow.get().show(srlProjects, view, article);
            return true;
        });
    }

    private void initRefreshView() {
        srlProjects.setOnLoadMoreListener(refreshLayout -> {
            mPageNum++;
            mPresenter.loadMoreProjects(mPageNum, mId);
            isLoadMore = true;
        });
        srlProjects.setOnRefreshListener(refreshLayout -> {
            mPageNum = 1;
            mPresenter.loadMoreProjects(1, mId);
            isLoadMore = false;
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadProjects(1, mId);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) return;
        Article article = mArticles.get(mArticlePosition);
        switch (requestCode){
            case Constant.REQUEST_LOGIN:
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
    public void showProjects(List<Article> articleList) {
        if(!CommonUtil.isEmptyList(mArticles)) mArticles.clear();
        mArticles.addAll(articleList);
        mProjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void collect() {
        if (mArticle == null) return;
        if(mArticle.isCollect())
            mPresenter.unCollectArticle(mArticle.getId());
        else
            mPresenter.collectArticle(mArticle.getId());
    }

    @Override
    public void showMoreProjects(List<Article> articleList) {
        if(isLoadMore){
            srlProjects.finishLoadMore();
        }else {
            if(!CommonUtil.isEmptyList(mArticles)) mArticles.clear();
            srlProjects.finishRefresh();
        }
        mArticles.addAll(articleList);
        mProjectsAdapter.notifyDataSetChanged();
    }

    @Override
    public void topping() {
        if(rvProjectList != null) rvProjectList.smoothScrollToPosition(0);
    }

    @Override
    public void collectArticleSuccess() {
        showToast(getString(R.string.toast_collection_success));
        mArticles.get(mArticlePosition).setCollect(true);
        mProjectsAdapter.notifyItemChanged(mArticlePosition + mProjectsAdapter.getHeaderLayoutCount());
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.toast_uncollection_success));
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
    public void autoRefresh() {
        srlProjects.autoRefresh();
    }

    @Override
    public void reLoad() {
        mPageNum = 1;
        mPresenter.loadProjects(1, mId);
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

    private String getHttps(String http){
        StringBuilder builder = new StringBuilder("https");
        builder.append(http.substring(http.indexOf(':')));
        return builder.toString();
    }
}
