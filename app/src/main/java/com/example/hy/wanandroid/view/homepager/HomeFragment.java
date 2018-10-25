package com.example.hy.wanandroid.view.homepager;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.base.fragment.BaseFragment;
import com.example.hy.wanandroid.contract.homepager.HomeContract;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.youth.banner.Banner;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

/**
 * 首页tab
 * Created by 陈健宇 at 2018/10/23
 */
public class HomeFragment extends BaseFragment<HomeContract.Presenter> implements HomeContract.View {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_articles)
    RecyclerView rvArticles;
    @BindView(R.id.srl_home)
    SmartRefreshLayout srlHome;
    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_homepager;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void initView() {

        tlCommon.setTitle(R.string.menu_btm_nav_home);
    }

    @Override
    protected void initData() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

}
