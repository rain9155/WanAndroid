package com.example.hy.wanandroid.view.hierarchy;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.component.activity.DaggerHierarchySecondActivityComponent;
import com.example.hy.wanandroid.di.component.activity.HierarchySecondActivityComponent;
import com.example.hy.wanandroid.di.module.activity.HierarchySecondActivityModule;
import com.example.hy.wanandroid.event.ToppingEvent;
import com.example.hy.wanandroid.utlis.RxBus;
import com.example.hy.wanandroid.utlis.StatusBarUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class HierarchySecondActivity extends BaseActivity {

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.vp_hierarchy_second)
    ViewPager vpHierarchySecond;
    @BindView(R.id.tv_common_title)
    TextView tvCommonTitle;
    @BindView(R.id.iv_common_search)
    ImageView ivCommonSearch;
    @BindView(R.id.fbtn_up)
    FloatingActionButton fbtnUp;

    @Inject
    List<Fragment> mFragments;
    @Inject
    List<String> mTitles;
    @Inject
    List<Integer> mIds;

    private FragmentPagerAdapter mPagerAdapter;
    private HierarchySecondActivityComponent mComponent;
    private String mTitle;

    @Override
    protected void inject() {
        mComponent = DaggerHierarchySecondActivityComponent.builder().appComponent(getAppComponent()).hierarchySecondActivityModule(new HierarchySecondActivityModule()).build();
        mComponent.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hierarchy_second;
    }

    @Override
    protected void initView() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT <Build.VERSION_CODES.LOLLIPOP)
            StatusBarUtil.setHeightAndPadding(this, tlCommon);
        Intent intent = getIntent();
        for (String s : intent.getStringArrayListExtra(Constant.KEY_HIERARCHY_ID))
            mIds.add(Integer.valueOf(s));
        mTitles = intent.getStringArrayListExtra(Constant.KEY_HIERARCHY_NAMES);
        mTitle = intent.getStringExtra(Constant.KEY_HIERARCHY_NAME);
        initToolBar();
        initViewPager();
        fbtnUp.setOnClickListener(v -> RxBus.getInstance().post(new ToppingEvent()));
    }

    @Override
    protected void initData() {

    }

    private void initViewPager() {
        for (int i = 0; i < mTitles.size(); i++) {
            commonTablayout.addTab(commonTablayout.newTab().setText(mTitles.get(i)));
            mFragments.add(HierarchySecondFragment.newInstance(mIds.get(i)));
        }
        mPagerAdapter = new VpAdapter(getSupportFragmentManager(), mFragments, mTitles);
        vpHierarchySecond.setAdapter(mPagerAdapter);
        vpHierarchySecond.setOffscreenPageLimit(mTitles.size());
        commonTablayout.setupWithViewPager(vpHierarchySecond);
    }

    private void initToolBar() {
        setSupportActionBar(tlCommon);
        ivCommonSearch.setVisibility(View.INVISIBLE);
        tvCommonTitle.setText(mTitle);
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());
    }


    public HierarchySecondActivityComponent getComponent() {
        return mComponent;
    }

    public static void startActivity(Context context, String name, ArrayList<String> listId, ArrayList<String> listName) {
        Intent intent = new Intent(context, HierarchySecondActivity.class);
        intent.putStringArrayListExtra(Constant.KEY_HIERARCHY_ID, listId);
        intent.putStringArrayListExtra(Constant.KEY_HIERARCHY_NAMES, listName);
        intent.putExtra(Constant.KEY_HIERARCHY_NAME, name);
        context.startActivity(intent);
    }
}
