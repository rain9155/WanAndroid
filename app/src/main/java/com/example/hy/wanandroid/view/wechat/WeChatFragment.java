package com.example.hy.wanandroid.view.wechat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.commonlib.utils.StatusBarUtil;
import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.base.fragment.BaseLoadFragment;
import com.example.hy.wanandroid.contract.wechat.WeChatContract;
import com.example.hy.wanandroid.di.module.fragment.WeChatFragmentModule;
import com.example.hy.wanandroid.model.network.entity.Tab;
import com.example.hy.wanandroid.presenter.wechat.WeChatPresenter;
import com.example.hy.wanandroid.view.MainActivity;
import com.example.hy.wanandroid.view.navigation.NavigationActivity;
import com.example.hy.wanandroid.view.search.SearchActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
 * 微信公众号tab
 * Created by 陈健宇 at 2018/12/19
 */
public class WeChatFragment extends BaseLoadFragment<WeChatPresenter> implements WeChatContract.View {

    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.normal_view)
    ViewPager vpWeChats;

    @Inject
    WeChatPresenter mPresenter;
    @Inject
    List<String> mTitles;
    @Inject
    List<Integer> mIds;
    @Inject
    List<Fragment> mFragments;

    @Override
    protected WeChatPresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void inject() {
        if (!(getActivity() instanceof MainActivity)) return;
        ((MainActivity) getActivity()).getComponent().getWeChatFragmentComponent(new WeChatFragmentModule()).inject(this);
    }

    @Override
    protected void initView() {
       super.initView();
        StatusBarUtil.setHeightAndPadding(mActivity, tlCommon);
        initToolBar();
    }

    private void initToolBar() {
        ivCommonSearch.setVisibility(View.VISIBLE);
        tvCommonTitle.setText(R.string.homeFragment_wechat);
        tlCommon.setNavigationIcon(R.drawable.ic_navigation);
        tlCommon.setNavigationOnClickListener(v -> NavigationActivity.startActivity(mActivity));
        ivCommonSearch.setOnClickListener(v -> SearchActivity.startActivity(mActivity));
    }

    @Override
    protected void loadData() {
        mPresenter.loadWeChatTabs();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_wechat;
    }

    @Override
    public void showWeChatTabs(List<Tab> tabs) {
        for (Tab tab : tabs) {
            mIds.add(tab.getId());
            mTitles.add(tab.getName());
        }
        for (int i = 0; i < tabs.size(); i++) {
            mFragments.add(WeChatsFragment.newInstance(mIds.get(i)));
        }
        VpAdapter vpAdapter = new VpAdapter(getChildFragmentManager(), mFragments, mTitles);
        vpWeChats.setAdapter(vpAdapter);
        vpWeChats.setOffscreenPageLimit(mTitles.size());
        commonTablayout.setupWithViewPager(vpWeChats);
    }

    @Override
    public void reLoad() {
        super.reLoad();
        mPresenter.loadWeChatTabs();
    }


    public static WeChatFragment newInstance() {
        return new WeChatFragment();
    }

}
