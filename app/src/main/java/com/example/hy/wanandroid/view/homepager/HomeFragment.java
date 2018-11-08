package com.example.hy.wanandroid.view.homepager;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.ArticlesAdapter;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.example.hy.wanandroid.di.module.fragment.HomeFragmentModule;
import com.example.hy.wanandroid.network.entity.homepager.Article;
import com.example.hy.wanandroid.network.entity.homepager.BannerData;
import com.example.hy.wanandroid.presenter.homepager.HomePresenter;
import com.example.hy.wanandroid.utils.BannerImageLoader;
import com.example.hy.wanandroid.utils.CommonUtil;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 首页tab
 * Created by 陈健宇 at 2018/10/23
 */
public class HomeFragment extends BaseLoadFragment implements HomeContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.rv_articles)
    RecyclerView rvArticles;
    @BindView(R.id.normal_view)
    SmartRefreshLayout srlHome;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;

    @Inject
    HomePresenter mPresenter;
    @Inject
    @Named("bannerTitles")
    List<String> bannerTitles;
    @Inject
    @Named("bannerImages")
    List<String> bannerImages;
    @Inject
    List<Article> mArticles;
    @Inject
    LinearLayoutManager mLinearLayoutManager;
    @Inject
    ArticlesAdapter mArticlesAdapter;

    private int pageNum = 0;//首页文章页数
    private boolean isLoadMore = false;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepager;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getHomFragmentSubComponent(new HomeFragmentModule()).inject(this);
        mPresenter.attachView(this);

        //标题栏
        ivCommonSearch.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(R.string.homeFragment_home);
        tlCommon.setNavigationIcon(R.drawable.ic_navigation);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(_mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(_mActivity));

        //首页文章
        rvArticles.setLayoutManager(mLinearLayoutManager);
        mArticlesAdapter.openLoadAnimation();
        rvArticles.setAdapter(mArticlesAdapter);
        mArticlesAdapter.setOnItemClickListener((adapter, view, position) -> {
            Article article = mArticles.get(position);
            ArticleActivity.startActivity(_mActivity, article.getLink(), article.getTitle(), article.isCollect());
        });
        srlHome.setOnLoadMoreListener(refreshLayout -> {
            pageNum++;
            mPresenter.loadMoreArticles(pageNum);
            isLoadMore = true;
        });
        srlHome.setOnRefreshListener(refreshLayout -> {
            mPresenter.loadMoreArticles(0);
            mPresenter.loadBannerDatas();
            isLoadMore = false;
        });
    }

    @Override
    protected void loadData() {
        super.loadData();
        mPresenter.subscribleEvent();
        mPresenter.loadBannerDatas();
        mPresenter.loadArticles(0);
    }

    @Override
    public void showBannerDatas(List<BannerData> bannerDataList) {
        //获得标题,图片
        for (BannerData bannerData : bannerDataList) {
            bannerTitles.add(bannerData.getTitle());
            bannerImages.add(bannerData.getImagePath());
        }
        //设置banner
        banner.setImageLoader(new BannerImageLoader())
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)//显示圆形指示器和标题（水平显示）
                .setImages(bannerImages)//设置图片集合
                .setBannerAnimation(Transformer.BackgroundToForeground)//设置轮播动画
                .setBannerTitles(bannerTitles)//设置标题集合
                .setIndicatorGravity(BannerConfig.RIGHT)//设置指示器位置，右边
                .setDelayTime(2000)//设置轮播事件间隔
                .setOnBannerListener(position -> {
                    //跳转到详情
                })//设置点击事件，下标从零开始
                .start();
    }

    @Override
    public void showArticles(List<Article> articleList) {
        mArticles.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMoreArticles(List<Article> articleList) {
        if (isLoadMore) {
            srlHome.finishLoadMore();
        } else {
            if (!CommonUtil.isEmptyList(articleList)) mArticles.clear();
            srlHome.finishRefresh();
        }
        mArticles.addAll(articleList);
        mArticlesAdapter.notifyDataSetChanged();
    }

    @Override
    public void topping() {
        if(rvArticles != null) rvArticles.smoothScrollToPosition(0);
    }

    @Override
    public void unableRefresh() {
        if(isLoadMore) srlHome.finishLoadMore(); else srlHome.finishRefresh();
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadBannerDatas();
        mPresenter.loadArticles(0);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (banner != null) {
            banner.startAutoPlay();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (banner != null) {
            banner.stopAutoPlay();
        }
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
