package com.example.hy.wanandroid.view.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.adapter.FlowTagsAdapter;
import com.example.hy.wanandroid.adapter.HistoryAdapter;
import com.example.hy.wanandroid.base.activity.BaseLoadActivity;
import com.example.hy.wanandroid.entity.ArticleBean;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.search.SearchContract;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.entity.HotKey;
import com.example.hy.wanandroid.presenter.search.SearchPresenter;
import com.example.hy.wanandroid.utlis.AnimUtil;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.view.homepager.ArticleActivity;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.example.hy.wanandroid.widget.popup.PressPopup;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dagger.Lazy;

public class SearchActivity extends BaseLoadActivity<SearchPresenter> implements SearchContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.tv_hot_search)
    TextView tvHotSearch;
    @BindView(R.id.tfl_tag)
    TagFlowLayout tflTag;
    @BindView(R.id.tv_search_history)
    TextView tvSearchHistory;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.tv_hot_hint)
    TextView tvHotHint;
    @BindView(R.id.tv_history_hint)
    TextView tvHistoryHint;
    @BindView(R.id.cl_history_hot)
    ConstraintLayout clHistoryHot;
    @BindView(R.id.rv_search_request)
    RecyclerView rvSearchRequest;
    @BindView(R.id.normal_view)
    SmartRefreshLayout normalView;
    @BindView(R.id.rl_container)
    RelativeLayout rlContainer;

    @Inject
    SearchPresenter mPresenter;
    @Inject
    LinearLayoutManager mHistoriesManager, mSearchRequestManager;
    @Inject
    ArticlesAdapter mSearchResquestAdapter;
    @Inject
    HistoryAdapter mHistoryAdapter;
    @Inject
    List<HotKey> mHotKeyList;
    @Inject
    Lazy<PressPopup> mPopupWindow;

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private FlowTagsAdapter mFlowTagsAdapter;
    private boolean isLoadMore = false;
    private int mPageNum = 0;
    private int mArticlePosition = 0;//点击的位置
    private Article mArticle;
    private boolean isPress = false;
    private List<Article> mSearchResquestList;
    private List<String> mHistoryList;

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected SearchPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void initView() {
        super.initView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        initToolBar();
        initHistoryView();
        initSearchRequestView();
        initRefreshView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initSearchRequestView() {
        mSearchResquestList = mSearchResquestAdapter.getData();
        //搜索结果
        mSearchResquestAdapter.openLoadAnimation();
        rvSearchRequest.setLayoutManager(mSearchRequestManager);
        rvSearchRequest.setAdapter(mSearchResquestAdapter);
        mSearchResquestAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            if(CommonUtil.isEmptyList(mSearchResquestList)) return;
            mArticlePosition = position;
            mArticle = mSearchResquestList.get(position);
            ArticleBean articleBean = new ArticleBean(mArticle);
            ArticleActivity.startActivityForResult(SearchActivity.this, articleBean, false, Constant.REQUEST_REFRESH_ARTICLE);
        });
        mSearchResquestAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            if(CommonUtil.isEmptyList(mSearchResquestList)) return;
            mArticlePosition = position;
            mArticle =  mSearchResquestList.get(position);
            if(!User.getInstance().isLoginStatus()){
                LoginActivity.startActivityForResult(this, Constant.REQUEST_LOGIN);
                showToast(getString(R.string.first_login));
                return;
            }
            collect();
            AnimUtil.scale(view, -1);
        });
        mSearchResquestAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if(CommonUtil.isEmptyList(mSearchResquestList)) return false;
            Article article = mSearchResquestList.get(position);
            mPopupWindow.get().show(getWindow().getDecorView(), view, article);
            return true;
        });
    }

    private void initHistoryView() {
        mHistoryList = mHistoryAdapter.getData();
        //历史记录
        mHistoryAdapter.openLoadAnimation();
        rvHistory.setLayoutManager(mHistoriesManager);
        rvHistory.setAdapter(mHistoryAdapter);
        mHistoryAdapter.setOnItemClickListener((adapter, view, position) -> mSearchView.setQuery(mHistoryList.get(position), true));
        mHistoryAdapter.setOnItemChildClickListener((adapter, view, position) -> mPresenter.deleteOneHistoryRecord(mHistoryList.get(position)));
        tvClear.setOnClickListener(v -> mPresenter.deleteAllHistoryRecord());
    }

    private void initRefreshView() {
        normalView.setOnRefreshListener(refreshLayout -> {
            isLoadMore = false;
            mPageNum = 0;
            mPresenter.loadSearchMoreResquest(mSearchView.getQuery().toString(), 0);
        });
        normalView.setOnLoadMoreListener(refreshLayout -> {
            isLoadMore = true;
            mPageNum++;
            mPresenter.loadSearchMoreResquest(mSearchView.getQuery().toString(), mPageNum);
        });
        normalView.setEnableLoadMore(false);
        normalView.setEnableRefresh(false);
    }

    private void initToolBar() {
        //标题栏
        setSupportActionBar(tlCommon);
        tlCommon.setTitle("");
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tlCommon.setNavigationOnClickListener(v -> {
            if(clHistoryHot.getVisibility() == View.INVISIBLE){
                hideSearchRequestLayout();
                showHistoryHotLayout();
            }else {
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadHotkey();
        mPresenter.loadHistories();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        if (CommonUtil.isEmptyList(mSearchResquestList)) return;
        Article article = mSearchResquestList.get(mArticlePosition);
        switch (requestCode) {
            case Constant.REQUEST_LOGIN:
                if (article.isCollect()) mPresenter.unCollectArticle(article.getId());
                else mPresenter.collectArticle(article.getId());
                break;
            case Constant.REQUEST_REFRESH_ARTICLE:
                boolean isCollect = data.getBooleanExtra(Constant.KEY_DATA_RETURN, false);
                if (article.isCollect() != isCollect) {
                    article.setCollect(isCollect);
                    mSearchResquestAdapter.notifyItemChanged(mArticlePosition + mSearchResquestAdapter.getHeaderLayoutCount());
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        //得到SearchView
        mSearchView = (SearchView)menuItem.getActionView();
        mSearchAutoComplete = mSearchView.findViewById(R.id.search_src_text);
        mSearchView.setMaxWidth(R.dimen.dp_400);//设置最大宽度
        mSearchView.setSubmitButtonEnabled(true);//设置是否显示搜索框展开时的提交按钮
        mSearchView.setQueryHint(getResources().getString(R.string.searchActivity_hint));//设置输入框提示语
        mSearchView.onActionViewExpanded();//设置搜索框直接展开显示。左侧有放大镜(在搜索框中) 右侧无叉叉 有输入内容后有叉叉 不能关闭搜索框
        mSearchAutoComplete.setTextColor(getResources().getColor(R.color.white));//设置内容文字颜色
        //搜索框文字变化监听，搜索按钮监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mPresenter.addHistoryRecord(query);
                mPresenter.loadSearchResquest(query, 0);
                isLoadMore = false;
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mPresenter.clearAllSearchKey(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if(clHistoryHot.getVisibility() == View.INVISIBLE){
            hideSearchRequestLayout();
            showHistoryHotLayout();
        }else {
            finish();
        }

    }

    @Override
    public void showHotKey(List<HotKey> hotKeyList) {
        if(!CommonUtil.isEmptyList(mHistoryList)) mHotKeyList.clear();
        mHotKeyList.addAll(hotKeyList);
        mFlowTagsAdapter = new FlowTagsAdapter(hotKeyList, tflTag);
        tflTag.setAdapter(mFlowTagsAdapter);
        tflTag.setOnTagClickListener((view, position, parent) -> {
            mSearchView.setQuery(mHotKeyList.get(position).getName(), true);
            //搜索
            mPresenter.loadSearchResquest(mHotKeyList.get(position).getName(), 0);
            return false;
        });
    }

    @Override
    public void showSearchResquest(List<Article> searchRequestList) {
        if(!CommonUtil.isEmptyList(mSearchResquestList)) mSearchResquestList.clear();
        mSearchResquestList.addAll(searchRequestList);
        mSearchResquestAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSearchMoreResquest(List<Article> searchRequestList) {
        if (isLoadMore) {
            normalView.finishLoadMore();
        } else {
            normalView.finishRefresh();
            if (!CommonUtil.isEmptyList(mSearchResquestList)) mSearchResquestList.clear();
        }
        mSearchResquestList.addAll(searchRequestList);
        mSearchResquestAdapter.notifyDataSetChanged();
    }

    @Override
    public void showHistoryHotLayout() {
        clHistoryHot.setVisibility(View.VISIBLE);
        normalView.setEnableLoadMore(false);
        normalView.setEnableRefresh(false);
    }

    @Override
    public void hideHistoryHotLayout() {
        clHistoryHot.setVisibility(View.INVISIBLE);
        normalView.setEnableLoadMore(true);
        normalView.setEnableRefresh(true);
    }

    @Override
    public void showSearchRequestLayout() {
        rlContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideSearchRequestLayout() {
        if(!CommonUtil.isEmptyList(mSearchResquestList)) mSearchResquestList.clear();
        rlContainer.setVisibility(View.GONE);
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
    public void showEmptyLayout() {
    }

    @Override
    public void hideEmptyLayout() {
    }

    @Override
    public void showHistories(List<String> histories) {
        if(!CommonUtil.isEmptyList(mHistoryList)) mHistoryList.clear();
        mHistoryList.addAll(histories);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void addOneHistorySuccess(String record) {
        mHistoryList.add(0, record);
        mHistoryAdapter.notifyDataSetChanged();
        tvHistoryHint.setVisibility(View.INVISIBLE);
    }

    @Override
    public void deleteOneHistorySuccess(String record) {
        mHistoryList.remove(record);
        mHistoryAdapter.notifyDataSetChanged();
        if (CommonUtil.isEmptyList(mHistoryList)) tvHistoryHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void deleteAllHistoryRecordSuccess() {
        if (!CommonUtil.isEmptyList(mHistoryList)) {
            mHistoryList.clear();
            mHistoryAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showHistoryHintLayout() {
        tvHistoryHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHistoryHintLayout() {
        tvHistoryHint.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showHotHintLayout() {
        tvHotHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideHotHintLayout() {
        tvHotHint.setVisibility(View.INVISIBLE);
    }

    @Override
    public void collectArticleSuccess() {
        showToast(getString(R.string.toast_collection_success));
        mSearchResquestList.get(mArticlePosition).setCollect(true);
        mSearchResquestAdapter.notifyItemChanged(mArticlePosition + mSearchResquestAdapter.getHeaderLayoutCount());
    }

    @Override
    public void unCollectArticleSuccess() {
        showToast(getString(R.string.toast_uncollection_success));
        mSearchResquestList.get(mArticlePosition).setCollect(false);
        mSearchResquestAdapter.notifyItemChanged(mArticlePosition + mSearchResquestAdapter.getHeaderLayoutCount());
    }

    @Override
    public void reLoad() {
        mPageNum = 0;
        mPresenter.loadSearchResquest(mSearchView.getQuery().toString(), 0);
        mPresenter.loadHotkey();
    }

    @Override
    public void showLoading() {
        showSearchRequestLayout();
        super.showLoading();
    }



    @Override
    public void unableRefresh() {
        if(isLoadMore) normalView.finishLoadMore(); else normalView.finishRefresh();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
