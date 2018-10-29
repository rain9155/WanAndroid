package com.example.hy.wanandroid.view.hierarchy;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

import android.content.Context;
import android.content.Intent;

import com.example.hy.wanandroid.R;
import com.example.hy.wanandroid.adapter.VpAdapter;
import com.example.hy.wanandroid.base.activity.BaseActivity;
import com.example.hy.wanandroid.config.Constant;
import com.example.hy.wanandroid.di.component.activity.DaggerHierarchySecondActivityComponent;
import com.example.hy.wanandroid.di.component.activity.HierarchySecondActivityComponent;
import com.example.hy.wanandroid.di.module.activity.HierarchySecondActivityModule;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class HierarchySecondActivity extends BaseActivity{

    @BindView(R.id.tl_common)
    Toolbar tlCommon;
    @BindView(R.id.common_tablayout)
    TabLayout commonTablayout;
    @BindView(R.id.vp_hierarchy_second)
    ViewPager vpHierarchySecond;

    @Inject
    List<SupportFragment> mFragments;
    @Inject
    List<String> mTitles;
    @Inject
    List<Integer> mIds;

    private FragmentPagerAdapter mPagerAdapter;
    private HierarchySecondActivityComponent mComponent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_hierarchy_second;
    }

    @Override
    protected void initView() {
        mComponent = DaggerHierarchySecondActivityComponent.builder().appComponent(getAppComponent()).hierarchySecondActivityModule(new HierarchySecondActivityModule()).build();
        mComponent.inject(this);

        Intent intent = getIntent();
        for(String s : intent.getStringArrayListExtra(Constant.KEY_ID)) mIds.add(Integer.valueOf(s));
        mTitles = intent.getStringArrayListExtra(Constant.KEY_NAMES);
        for(int i = 0; i < mTitles.size(); i++){
            commonTablayout.addTab(commonTablayout.newTab().setText(mTitles.get(i)));
            mFragments.add(HierarchySecondFragment.newInstance(mIds.get(i)));
        }
        vpHierarchySecond.setAdapter(mPagerAdapter);
        vpHierarchySecond.setOffscreenPageLimit(mTitles.size());
        commonTablayout.setupWithViewPager(vpHierarchySecond);
        mPagerAdapter = new VpAdapter(getSupportFragmentManager(), mFragments, mTitles);

        tlCommon.setTitle(intent.getStringExtra(Constant.KEY_NAME));
        tlCommon.setNavigationIcon(R.drawable.ic_arrow_left);
        tlCommon.setNavigationOnClickListener(v -> finish());
    }

    @Override
    protected void initData() {

    }

    public HierarchySecondActivityComponent getComponent() {
        return mComponent;
    }

    public static void startActivity(Context context, String name, ArrayList<String> listId, ArrayList<String> listName){
        Intent intent = new Intent(context, HierarchySecondActivity.class);
        intent.putStringArrayListExtra(Constant.KEY_ID, listId);
        intent.putStringArrayListExtra(Constant.KEY_NAMES, listName);
        intent.putExtra(Constant.KEY_NAME, name);
        context.startActivity(intent);
    }
}
