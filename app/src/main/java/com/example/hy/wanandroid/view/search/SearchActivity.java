package com.example.hy.wanandroid.view.search;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.contract.search.SearchContract;
import com.example.hy.wanandroid.di.component.activity.DaggerSearchActivityComponent;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.search.HotKey;
import com.example.hy.wanandroid.presenter.search.SearchPresenter;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends BaseActivity implements SearchContract.View {

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

    @Inject
    SearchPresenter mPresenter;
    @Inject
    LinearLayoutManager mHistoriesManager, mSearchRequestManager;
    @Inject
    List<Article> mSearchResquestList;
    @Inject
    List<String> mHistoryList;
    @Inject
    ArticlesAdapter mSearchResquestAdapter;
    @Inject
    HistoryAdapter mHistoryAdapter;

    private SearchView mSearchView;
    private SearchView.SearchAutoComplete mSearchAutoComplete;
    private FlowTagsAdapter mFlowTagsAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        DaggerSearchActivityComponent.builder().appComponent(getAppComponent()).build().inject(this);
        mPresenter.attachView(this);

        setSupportActionBar(tlCommon);
        tlCommon.setTitle("");
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tlCommon.setNavigationOnClickListener(v -> finish());

        mHistoryAdapter.openLoadAnimation();
        rvHistory.setLayoutManager(mHistoriesManager);
        rvHistory.setAdapter(mHistoryAdapter);
    }

    @Override
    protected void initData() {
        mPresenter.loadHotkey();
        mPresenter.loadHistories();
    }

    @Override
    public void showHotKey(List<HotKey> hotKeyList) {
        mFlowTagsAdapter = new FlowTagsAdapter(hotKeyList, tflTag);
        tflTag.setAdapter(mFlowTagsAdapter);
        tflTag.setOnTagClickListener((view, position, parent) -> {
            //搜索
            return false;
        });
    }

    @Override
    public void showSearchResquest(List<Article> searchRequestList) {

    }

    @Override
    public void showHistories(List<String> histories) {
        mHistoryList.addAll(histories);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public void addOneHistory(String record) {
        mHistoryList.add(0, record);
        mHistoryAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tl_search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.item_search);
        //得到SearchView
        mSearchView = (SearchView) MenuItemCompat.getActionView(menuItem);
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
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
            mPresenter = null;
        }
        super.onDestroy();
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SearchActivity.class);
        context.startActivity(intent);
    }
}
