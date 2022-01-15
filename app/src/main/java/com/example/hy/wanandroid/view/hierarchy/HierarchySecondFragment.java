package com.example.hy.wanandroid.view.hierarchy;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.entity.ArticleBean;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.hierarchy.HierarchySecondContract;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.presenter.hierarchy.HierarchySecondPresenter;
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
 * Created by 陈健宇 at 2018/10/28
 */
public class HierarchySecondFragment extends BaseLoadFragment<HierarchySecondPresenter> implements HierarchySecondContract.View {

    @BindView(R.id.rv_hierarchy)
    RecyclerView rvHierarchySecondList;
    @BindView(R.id.srl_hierarchy_second)
    SmartRefreshLayout srlHierarchyList;

    @Inject
    HierarchySecondPresenter mPresenter;
    @Inject
    ArticlesAdapter mArticlesAdapter;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    Lazy<PressPopup> mPopupWindow;

    private int mPageNum = 0;
    private int mId = -1;
    private boolean isLoadMore = false;
    private int mArticlePosition = 0;//点击的位置
    private Article mArticle;
    private boolean isPress = false;
    private List<Article> mArticleList;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_hierarchy_second;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if(bundle != null){
            mId = bundle.getInt(Constant.KEY_HIERARCHY_PAGENUM, -1);
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected HierarchySecondPresenter getPresenter() {
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
        mArticleList = mArticlesAdapter.getData();
        mArticlesAdapter.openLoadAnimation();
        rvHierarchySecondList.setHasFixedSize(true);
        rvHierarchySecondList.setLayoutManager(mLinearLayoutManager);
        rvHierarchySecondList.setAdapter(mArticlesAdapter);
        mArticlesAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            if(CommonUtil.isEmptyList(mArticleList)) return;
            mArticlePosition = position;
            mArticle = mArticleList.get(position);
            ArticleBean articleBean = new ArticleBean(mArticle);
            ArticleActivity.startActicityForResultByFragment(mActivity, this, articleBean, false, Constant.REQUEST_REFRESH_ARTICLE);
        });
        mArticlesAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            if(CommonUtil.isEmptyList(mArticleList)) return;
            mArticlePosition = position;
            mArticle = mArticleList.get(position);
            if(!User.getInstance().isLoginStatus()) {
                LoginActivity.startActivityForResultByFragment(mActivity, this, Constant.REQUEST_LOGIN);
                showToast(getString(R.string.first_login));
                return;
            }
            collect();
            AnimUtil.scale(view, -1);
        });
        mArticlesAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if(CommonUtil.isEmptyList(mArticleList)) return false;
            Article article = mArticleList.get(position);
            mPopupWindow.get().show(srlHierarchyList, view, article);
            return true;
        });
    }

    private void initRefreshView() {
        srlHierarchyList.setOnRefreshListener(refreshLayout -> {
            mPageNum = 0;
            mPresenter.loadMoreArticles(0, mId);
            isLoadMore = false;
        });
        srlHierarchyList.setOnLoadMoreListener(refreshLayout -> {
            mPageNum++;
            mPresenter.loadMoreArticles(mPageNum, mId);
            isLoadMore = true;
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadArticles(0, mId);
    }


    @Override
    public void showArticles(List<Article> articleList) {
        if(!CommonUtil.isEmptyList(mArticleList)) mArticleList.clear();
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
        showToast(getString(R.string.toast_collection_success));
        mArticleList.get(mArticlePosition).setCollect(true);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.toast_uncollection_success));
        mArticleList.get(mArticlePosition).setCollect(false);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
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
        mPageNum = 0;
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
            case Constant.REQUEST_LOGIN:
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

    public static Fragment newInstance(int id){
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.KEY_HIERARCHY_PAGENUM, id);
        Fragment fragment = new HierarchySecondFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
}
