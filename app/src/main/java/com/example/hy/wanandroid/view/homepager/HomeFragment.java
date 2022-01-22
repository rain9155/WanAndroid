package com.example.hy.wanandroid.view.homepager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.entity.ArticleBean;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.config.User;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.entity.Article;
import com.example.hy.wanandroid.entity.BannerData;
import com.example.hy.wanandroid.presenter.homepager.HomePresenter;
import com.example.hy.wanandroid.utlis.AnimUtil;
import com.example.hy.wanandroid.utlis.BannerImageLoader;
import com.example.hy.wanandroid.utlis.CommonUtil;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.example.hy.wanandroid.view.mine.LoginActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;
import com.example.hy.wanandroid.widget.popup.PressPopupWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dagger.Lazy;

import static android.app.Activity.RESULT_OK;

/**
 * 首页tab
 * Created by 陈健宇 at 2018/10/23
 */
public class HomeFragment extends BaseLoadFragment<HomePresenter> implements HomeContract.View{

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_articles)
    RecyclerView rvArticles;
    @BindView(R.id.normal_view)
    SmartRefreshLayout srlHome;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;

    @Inject
    HomePresenter mPresenter;
    @Inject
    List<String> mBannerTitles;
    @Inject
    List<String> mBannerImages;
    @Inject
    List<String> mBannerAddress;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ArticlesAdapter mArticlesAdapter;
    @Inject
    Lazy<PressPopupWindow> mPopupWindow;

    private int mPageNum = 0;//首页文章页数
    private boolean isLoadMore = false;
    private int mArticlePosition = 0;//点击的位置
    private Article mArticle;//点击的文章
    private Banner mBanner;
    private boolean isPress = false;
    private List<Article> mArticles;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepager;
    }

    @Override
    protected HomePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        super.initView();
        StatusBarUtil.setHeightAndPadding(mActivity, tlCommon);
        initToolBar();
        initRecyclerView();
        initRefreshView();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initRecyclerView() {
        mArticles = mArticlesAdapter.getData();
        //首页文章
        View bannerLayout = LayoutInflater.from(mActivity).inflate(R.layout.banner_layout, null);
        mBanner = bannerLayout.findViewById(R.id.banner);
        rvArticles.setLayoutManager(mLinearLayoutManager);
        mArticlesAdapter.openLoadAnimation();
        mArticlesAdapter.addHeaderView(bannerLayout);
        rvArticles.setAdapter(mArticlesAdapter);

        mArticlesAdapter.setOnItemClickListener((adapter, view, position) -> {//跳转文章
            if(CommonUtil.isEmptyList(mArticles)) {
                return;
            }
            mArticlePosition = position;
            mArticle = mArticles.get(position);
            ArticleBean articleBean = new ArticleBean(mArticle);
            ArticleActivity.startActicityForResultByFragment(mActivity, this, articleBean, false, Constant.REQUEST_REFRESH_ARTICLE);

        });
        mArticlesAdapter.setOnItemChildClickListener((adapter, view, position) -> {//收藏
            if(CommonUtil.isEmptyList(mArticles)) {
                return;
            }
            mArticlePosition = position;
            mArticle = mArticles.get(position);
            if(!User.getInstance().isLoginStatus()){
                LoginActivity.startActivityForResultByFragment(mActivity, this, Constant.REQUEST_LOGIN);
                showToast(getString(R.string.first_login));
                return;
            }
            collect();
            AnimUtil.scale(view, -1);
        });
        mArticlesAdapter.setOnItemLongClickListener((adapter, view, position) -> {
            if(CommonUtil.isEmptyList(mArticles)) {
                return false;
            }
            Article article = mArticles.get(position);
            mPopupWindow.get().show(srlHome, view, article);
            return true;
        });
    }

    private void initRefreshView() {
        srlHome.setOnLoadMoreListener(refreshLayout -> {
            mPageNum++;
            mPresenter.loadMoreArticles(mPageNum);
            isLoadMore = true;
        });
        srlHome.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadMoreArticles(0);
            mPresenter.loadBannerDatas();
            mPageNum = 0;
            isLoadMore = false;
        });
    }

    private void initToolBar() {
        //标题栏
        ivCommonSearch.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(R.string.homeFragment_home);
        tlCommon.setNavigationIcon(R.drawable.ic_navigation);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(mActivity));
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.loadBannerDatas();
        mPresenter.loadArticles(0);
    }

    @Override
    public void showBannerDatas(List<BannerData> bannerDataList) {
        if(!CommonUtil.isEmptyList(mBannerTitles)){
            mBannerTitles.clear();
            mBannerImages.clear();
            mBannerAddress.clear();
        }
        //获得标题,图片
        for (BannerData bannerData : bannerDataList) {
            mBannerTitles.add(bannerData.getTitle());
            mBannerImages.add(bannerData.getImagePath());
            mBannerAddress.add(bannerData.getUrl());
        }
        //设置banner
        mBanner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//显示圆形指示器和标题（水平显示）
                .setImages(mBannerImages)//设置图片集合
                .setBannerAnimation(Transformer.BackgroundToForeground)//设置轮播动画
                .setBannerTitles(mBannerTitles)//设置标题集合
                .setIndicatorGravity(BannerConfig.RIGHT)//设置指示器位置，右边
                .setDelayTime(2000)//设置轮播事件间隔
                .setOnBannerListener(position -> {
                    //跳转到详情
                    ArticleBean articleBean = new ArticleBean();
                    articleBean.setTitle(mBannerTitles.get(position));
                    articleBean.setLink(mBannerAddress.get(position));
                    articleBean.setCollect(false);
                    articleBean.setId(-1);
                    ArticleActivity.startActivity(mActivity, articleBean, true);
                })//设置点击事件，下标从零开始
                .start();
    }

    @Override
    public void showArticles(List<Article> articleList) {
        if(!CommonUtil.isEmptyList(mArticles)) {
            mArticles.clear();
        }
        mArticles.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreArticles(List<Article> articleList) {
        if (isLoadMore) {
            srlHome.finishLoadMore();
        } else {
            if (!CommonUtil.isEmptyList(mArticles)) {
                mArticles.clear();
            }
            srlHome.finishRefresh();
        }
        mArticles.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void topping() {
        if(rvArticles != null) {
            rvArticles.smoothScrollToPosition(0);
        }
    }

    @Override
    public void collectArticleSuccess() {
        mArticles.get(mArticlePosition).setCollect(true);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
        showToast(getString(R.string.toast_collection_success));
    }

    @Override
    public void unCollectArticleSuccess() {
        mArticles.get(mArticlePosition).setCollect(false);
        mArticlesAdapter.notifyItemChanged(mArticlePosition + mArticlesAdapter.getHeaderLayoutCount());
        showToast(getString(R.string.toast_uncollection_success));
    }

    @Override
    public void refreshCollections(List<Integer> ids) {
       for(int i = 0; i < ids.size(); i++){
          for(int j = 0; j < mArticles.size(); j++){
              if(mArticles.get(j).getId() == ids.get(i)){
                  mArticles.get(j).setCollect(false);
                  mArticlesAdapter.notifyItemChanged(j + mArticlesAdapter.getHeaderLayoutCount());
                  break;
              }
          }
       }
    }

    @Override
    public void collect() {
        if(mArticle == null) {
            return;
        }
        if(mArticle.isCollect()) {
            mPresenter.unCollectArticle(mArticle.getId());
        } else {
            mPresenter.collectArticle(mArticle.getId());
        }
    }

    @Override
    public void autoRefresh() {
        srlHome.autoRefresh();
    }

    @Override
    public void unableRefresh() {
        if(isLoadMore) {
            srlHome.finishLoadMore();
        } else {
            srlHome.finishRefresh();
        }
    }

    @Override
    public void reLoad() {
        mPageNum = 0;
        mPresenter.loadBannerDatas();
        mPresenter.loadArticles(0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_OK) {
            return;
        }
        if(CommonUtil.isEmptyList(mArticles)) {
            return;
        }
        Article article = mArticles.get(mArticlePosition);
        switch (requestCode){
            case Constant.REQUEST_LOGIN:
                if(article.isCollect()) {
                    mPresenter.unCollectArticle(article.getId());
                } else {
                    mPresenter.collectArticle(article.getId());
                }
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
    public void onStart() {
        super.onStart();
        if (mBanner != null) {
            mBanner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBanner != null) {
            mBanner.stopAutoPlay();
        }
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }
}
